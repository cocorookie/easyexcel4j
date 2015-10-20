package com.wanyi.common.excel4j;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

public class ExcelObjectMapperDO implements Serializable {

	private static final long serialVersionUID = 7745296502632710593L;

	/**
	 * ��Ӧ�������
	 */
	private String objectFieldName;
	/**
	 * ��Ӧ�����Ե�����
	 */
	private Class<?> objectFieldType;
	/**
	 * ��ӦExcel������
	 */
	private String excelColumnName;
	/**
	 * ��ӦExcel���к�
	 */
	private Integer excelColumnNum;
	/**
	 * Excel���Ƿ����
	 */
	private boolean required;
	/**
	 * ����ֵ��Ӧ��ϵ
	 */
	private Map<String, ?> valueMap = Maps.newHashMap();

	public String getObjectFieldName() {
		return objectFieldName;
	}

	public void setObjectFieldName(String objectFieldName) {
		this.objectFieldName = objectFieldName;
	}

	public String getExcelColumnName() {
		return excelColumnName;
	}

	public void setExcelColumnName(String excelColumnName) {
		this.excelColumnName = excelColumnName;
	}

	public Integer getExcelColumnNum() {
		return excelColumnNum;
	}

	public void setExcelColumnNum(Integer excelColumnNum) {
		this.excelColumnNum = excelColumnNum;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Class<?> getObjectFieldType() {
		return objectFieldType;
	}

	public void setObjectFieldType(Class<?> objectFieldType) {
		this.objectFieldType = objectFieldType;
	}

	@Override
	public String toString() {
		return this.excelColumnName.toString();
	}

	public Map<String, ?> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, ?> valueMap) {
		this.valueMap = valueMap;
	}

}