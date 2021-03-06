package com.wanyi.common.excel4j;

import com.google.common.collect.Maps;
import com.wanyi.common.excel4j.util.EasyExcelUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 在XML文件中定义excel的列与object的属性之间的对应关系
 * 
 * @author feiying.gh
 */
public class XmlMapperStrategy extends AbstractMapperStrategy {

	public <T> XmlMapperStrategy(Class<T> clazz, String... pathXml) throws Exception {
		super(new ExcelConfig(clazz));

		SAXReader reader = new SAXReader();
		for (String path : pathXml) {
			// 读取XML文件,获得document对象
			Document document = reader.read(new File(path));
			// 获取文档的根节点
			Element root = document.getRootElement();
			// 取得某节点的子节点
			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String className = elm.attributeValue("class");
				Class<?> temClass = ClassLoader.getSystemClassLoader().loadClass(className);

				List<ExcelObjectMapperDO> list = getMapperDOs(temClass);

				for (Iterator<?> columnIt = elm.elementIterator(); columnIt.hasNext();) {
					Element columnElm = (Element) columnIt.next();
					String columnName = columnElm.attributeValue("columnName");
					String fieldName = columnElm.attributeValue("fieldName");
					String required = columnElm.attributeValue("required");

					ExcelObjectMapperDO eom = getByFieldName(list, fieldName);
					eom.setExcelColumnName(columnName);
					eom.setObjectFieldName(fieldName);
					eom.setRequired(Boolean.valueOf(required));

					Class<?> type = temClass.getDeclaredField(fieldName).getType();
					eom.setObjectFieldType(type);

					if (type.getSimpleName().equalsIgnoreCase("boolean")) {
						Map<String, Boolean> valueMap = Maps.newHashMap();
						for (Iterator<?> valIt = columnElm.elementIterator(); valIt.hasNext();) {
							Element valElm = (Element) valIt.next();
							String name = valElm.attributeValue("key");
							String value = valElm.attributeValue("value");
							valueMap.put(name, Boolean.valueOf(value));
						}
						eom.setValueMap(valueMap);
					}

				}
			}
		}

	}

	private ExcelObjectMapperDO getByFieldName(List<ExcelObjectMapperDO> list, String fieldName) {
		for (ExcelObjectMapperDO excelObjectMapperDO : list) {
			if (excelObjectMapperDO.getObjectFieldName().equals(fieldName)) {
				return excelObjectMapperDO;
			}
		}
		ExcelObjectMapperDO eom = new ExcelObjectMapperDO();
		list.add(eom);
		return eom;
	}

	@Override
	public void init(FileItem fileItem) throws Exception {
		try {
			// 找到第一行
			Row row = EasyExcelUtils.getRow(fileItem, 0, 0);
			// 根据字段名称找列号
			Iterator<?> it = row.cellIterator();
			for (int i = 0; it.hasNext(); i++) {
				Cell cell = (Cell) it.next();
				for (ExcelObjectMapperDO eom : getMapperDOs()) {
					if (eom.getExcelColumnName().equals(EasyExcelUtils.getCellStringValue(cell))) {
						eom.setExcelColumnNum(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			clean();
			throw new Exception("init fileItem error.", e);
		}

	}
}
