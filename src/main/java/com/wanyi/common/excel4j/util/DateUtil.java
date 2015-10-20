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
        SimpleDateFormat chineseFormat=new SimpleDateFormat("yyyy年M月d日 HH:mm");
        SimpleDateFormat chineseFormat1=new SimpleDateFormat("yyyy年M月d日");
        SimpleDateFormat chineseFormat2=new SimpleDateFormat("HH:mm");
        SimpleDateFormat chineseFormat3=new SimpleDateFormat("M月d日 HH:mm");
        SimpleDateFormat chineseFormat4=new SimpleDateFormat("M月d日");
        formatMap.put("yyyyMMdd", yyyyMMddSdf);
        formatMap.put("yyyyMMddHHmmss", yyyyMMddHHmmssSdf);
        formatMap.put("yyyy-MM-dd", yyyy_MM_DDSdf);
        formatMap.put("yyyy-MM-dd HH:mm:ss", yyyy_MM_ddHHmmssSdf);
        formatMap.put("yyyy年M月d日 HH:mm",chineseFormat);
        formatMap.put("yyyy年M月d日",chineseFormat1);
        formatMap.put("HH:mm",chineseFormat2);
        formatMap.put("M月d日 HH:mm",chineseFormat3);
        formatMap.put("yyyy-MM-dd HH:mm",yyyyMMddHHmmSdf);
        formatMap.put("M月d日",chineseFormat4);

        formatList.add(yyyy_MM_ddHHmmssSdf);
        formatList.add(yyyyMMddHHmmSdf);
        formatList.add(yyyy_MM_DDSdf);
        formatList.add(yyyyMMddHHmmssSdf);
        formatList.add(yyyyMMddSdf);
    }

    /**
     * 根据字符串获取时间 <br/>
     * 会根据系统支持的格式一一判断，如果解析成功则返回
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
     * 获取固定格式的时间串
     *
     * @param format
     *            格式
     * @param date
     *            时间
     * @return 如果格式是不支持的格式或者参数有误，那么返回“”，而不是null
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
     * 这里做了个小处理，如果时分秒均为0，那么就不显示
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
        if(format.equals("yyyy年M月d日 HH:mm")){
            return dateStr.replaceAll(" 00:00$", "");
        }

        return dateStr;
    }

    /**
     * 这里如果时分秒都没有，那么会自动补全23：59：59
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
     * 去掉date的时分秒属性
     *
     * @param date
     * @return 去掉时分秒的时间
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
     * 把一个时间的时分秒属性设置为23:59:59
     *
     * @param date
     * @return 设置时分秒之后的时间
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
     * 取得系统当前日期
     */
    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取时间毫秒数
     * @return
     */
    public static long getTimeMillis(Date date) {
        return date.getTime();
    }

    /**
     * 获取日期距离零点的毫秒数
     * @param date
     * @return
     */
    public static long getMargin(Date date){
        return getMargin(date.getTime());
    }
    /**
     * 获取日期距离零点的毫秒数
     * @param timeInMils 毫秒数
     * @return
     */
    public static long getMargin(long timeInMils){
        return (timeInMils+8*60*60*1000)%(24*60*60*1000);//这里要加8小时，因为时区问题
    }

    /**
     * 日期增加或者减少多少天
     * @param date 日期
     * @param day 日期增量，当day大于0时，为日期增加day天，当day小于0时，为日期减少day天
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }


    /**
     * 获取endDate和startDate之间的天数差
     * @return 两者同一天，返回0， endDate早于startDate，返回负值
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
     * 比较两个日期是否相等
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
     * 返回：星期一，星期二这种字串
     * @param date
     * @return
     */
    public static String getWeekStr(Date date){
        if (date==null) return null;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
            case Calendar.SUNDAY:
                return "周日";
            default:
                return null;
        }
    }

}
