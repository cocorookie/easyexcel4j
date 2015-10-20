package com.wanyi.common.excel4j.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cocomao on 15/10/19.
 */
public class DateUtil {
    static Map<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>();
    static List<SimpleDateFormat> formatList=new ArrayList<SimpleDateFormat>();
    static {
        SimpleDateFormat yyyyMMddSdf=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat yyyyMMddHHmmssSdf=new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat yyyyMMddHHmmSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat yyyy_MM_ddHHmmssSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat yyyy_MM_DDSdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat chineseFormat=new SimpleDateFormat("yyyy��M��d�� HH:mm");
        SimpleDateFormat chineseFormat1=new SimpleDateFormat("yyyy��M��d��");
        SimpleDateFormat chineseFormat2=new SimpleDateFormat("HH:mm");
        SimpleDateFormat chineseFormat3=new SimpleDateFormat("M��d�� HH:mm");
        SimpleDateFormat chineseFormat4=new SimpleDateFormat("M��d��");
        formatMap.put("yyyyMMdd", yyyyMMddSdf);
        formatMap.put("yyyyMMddHHmmss", yyyyMMddHHmmssSdf);
        formatMap.put("yyyy-MM-dd", yyyy_MM_DDSdf);
        formatMap.put("yyyy-MM-dd HH:mm:ss", yyyy_MM_ddHHmmssSdf);
        formatMap.put("yyyy��M��d�� HH:mm",chineseFormat);
        formatMap.put("yyyy��M��d��",chineseFormat1);
        formatMap.put("HH:mm",chineseFormat2);
        formatMap.put("M��d�� HH:mm",chineseFormat3);
        formatMap.put("yyyy-MM-dd HH:mm",yyyyMMddHHmmSdf);
        formatMap.put("M��d��",chineseFormat4);

        formatList.add(yyyy_MM_ddHHmmssSdf);
        formatList.add(yyyyMMddHHmmSdf);
        formatList.add(yyyy_MM_DDSdf);
        formatList.add(yyyyMMddHHmmssSdf);
        formatList.add(yyyyMMddSdf);
    }

    /**
     * �����ַ�����ȡʱ�� <br/>
     * �����ϵͳ֧�ֵĸ�ʽһһ�жϣ���������ɹ��򷵻�
     * @param dateStr
     * @return
     */
    public static Date parseDateString(String dateStr){
        if(CheckUtil.isEmpty(dateStr)){
            return null;
        }
        try {
            for (SimpleDateFormat sdf:formatList) {
                try{
                    sdf.setLenient(false);
                    return sdf.parse(dateStr);
                }catch (Exception ex){
                    //do nothing
                }
            }
        }catch (Exception ex){
            return null;
        }
        return null;
    }

    /**
     * ��ȡ�̶���ʽ��ʱ�䴮
     *
     * @param format
     *            ��ʽ
     * @param date
     *            ʱ��
     * @return �����ʽ�ǲ�֧�ֵĸ�ʽ���߲���������ô���ء�����������null
     */
    public static String getFormatString(String format, Date date) {
        if (CheckUtil.isEmpty(format)) {
            return "";
        }
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = formatMap.get(format);
        if (sdf == null) {
            return "";
        }
        return sdf.format(date);
    }

    /**
     * �������˸�С�������ʱ�����Ϊ0����ô�Ͳ���ʾ
     * @param format
     * @param date
     * @return
     */
    public static String getFormatStringRemoveZero(String format, Date date) {
        if (CheckUtil.isEmpty(format)) {
            return "";
        }
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = formatMap.get(format);
        if (sdf == null) {
            return "";
        }
        String dateStr=sdf.format(date);
        if (format.equals("yyyy-MM-dd HH:mm:ss")) {
            return dateStr.replaceAll(" 00:00:00", "");
        }
        if (format.equals("yyyy-MM-dd HH:mm")) {
            return dateStr.replaceAll(" 00:00", "");
        }
        if (format.equals("yyyyMMddHHmmss")){
            return dateStr.replaceAll("000000$", "");
        }
        if(format.equals("yyyy��M��d�� HH:mm")){
            return dateStr.replaceAll(" 00:00$", "");
        }

        return dateStr;
    }

    /**
     * �������ʱ���붼û�У���ô���Զ���ȫ23��59��59
     * @param format
     * @param date
     * @return
     */
    public static String getFormatStringAndRoof(String format, Date date) {
        if (CheckUtil.isEmpty(format)) {
            return "";
        }
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = formatMap.get(format);
        if (sdf == null) {
            return "";
        }
        String dateStr=sdf.format(date);
        if (format.equals("yyyy-MM-dd HH:mm:ss")) {
            return dateStr.replaceAll(" 00:00:00", " 23:59:59");
        }
        if (format.equals("yyyyMMddHHmmss")){
            return dateStr.replaceAll("000000$", "235959");
        }
        return dateStr;
    }

    /**
     * ȥ��date��ʱ��������
     *
     * @param date
     * @return ȥ��ʱ�����ʱ��
     */
    public static Date floor(Date date) {
        if(date == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    /**
     * ��һ��ʱ���ʱ������������Ϊ23:59:59
     *
     * @param date
     * @return ����ʱ����֮���ʱ��
     */
    public static Date roof(Date date) {
        if(date == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND,999);
        return cal.getTime();
    }

    /**
     * ȡ��ϵͳ��ǰ����
     */
    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * ��ȡʱ�������
     * @return
     */
    public static long getTimeMillis(Date date) {
        return date.getTime();
    }

    /**
     * ��ȡ���ھ������ĺ�����
     * @param date
     * @return
     */
    public static long getMargin(Date date){
        return getMargin(date.getTime());
    }
    /**
     * ��ȡ���ھ������ĺ�����
     * @param timeInMils ������
     * @return
     */
    public static long getMargin(long timeInMils){
        return (timeInMils+8*60*60*1000)%(24*60*60*1000);//����Ҫ��8Сʱ����Ϊʱ������
    }

    /**
     * �������ӻ��߼��ٶ�����
     * @param date ����
     * @param day ������������day����0ʱ��Ϊ��������day�죬��dayС��0ʱ��Ϊ���ڼ���day��
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }


    /**
     * ��ȡendDate��startDate֮���������
     * @return ����ͬһ�죬����0�� endDate����startDate�����ظ�ֵ
     */
    public static long getDateInterval(Date startDate,Date endDate){
        long startTime=startDate.getTime()-DateUtil.getMargin(startDate.getTime());
        long endTime=endDate.getTime()-DateUtil.getMargin(endDate.getTime());

        if (endTime >= startTime){
            return (endTime-startTime)/(24*60*60*1000);
        }else{
            return 0-(startTime-endTime)/(24*60*60*1000);
        }
    }


    /**
     * �Ƚ����������Ƿ����
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateEqual(Date date1,Date date2){
        if (date1==null && date2==null) return true;
        if (date1!=null && date2==null) return false;
        if (date1==null && date2!=null) return false;
        return date1.getTime()==date2.getTime();
    }

    /**
     * ���أ�����һ�����ڶ������ִ�
     * @param date
     * @return
     */
    public static String getWeekStr(Date date){
        if (date==null) return null;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "��һ";
            case Calendar.TUESDAY:
                return "�ܶ�";
            case Calendar.WEDNESDAY:
                return "����";
            case Calendar.THURSDAY:
                return "����";
            case Calendar.FRIDAY:
                return "����";
            case Calendar.SATURDAY:
                return "����";
            case Calendar.SUNDAY:
                return "����";
            default:
                return null;
        }
    }

}
