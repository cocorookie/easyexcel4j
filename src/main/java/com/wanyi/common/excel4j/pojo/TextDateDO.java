package com.wanyi.common.excel4j.pojo;

import com.wanyi.common.excel4j.util.SmartAnalyzeTimeUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cocomao on 15/10/19.
 */
public class TextDateDO {

    /**
     * 返回的日期是match到的，还是说是默认值（今天）
     */
    private boolean isMatched=false;

    /**
     * 年份
     */
    private int year=-1;
    /**
     * 匹配的年份字符串
     */
    private String yearMatchedStr;
    /**
     * 月份
     */
    private int month=-1;
    /**
     * 匹配的月份字符串
     */
    private String monthMatchedStr;
    /**
     * 本月第几天
     */
    private int dayInMonth = -1;
    /**
     * 本月第几周
     */
    private int weekInMonth = -1;
    /**
     * 本周第几天
     */
    private int dayInWeek = -1;
    /**
     * 具体日期匹配的字符串
     */
    private String dayMatchedStr;
    /**
     * 开始时间小时
     */
    private int startHour=0;
    /**
     * 开始时间分钟
     */
    private int startMinute = 0;
    /**
     * 结束时间小时
     */
    private int endHour=-1;
    /**
     * 结束时间分钟
     */
    private int endMinute = -1;
    /**
     * 开始时间匹配的字符串
     */
    private String startTimeMatchedStr;
    /**
     * 结束时间匹配的字符串
     */
    private String endTimeMatchedStr;
    /**
     * 匹配处理之后的字符串
     */
    private String resultStr;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayInMonth() {
        return dayInMonth;
    }

    public void setDayInMonth(int dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    public int getWeekInMonth() {
        return weekInMonth;
    }

    public void setWeekInMonth(int weekInMonth) {
        this.weekInMonth = weekInMonth;
    }

    public int getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(int dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getYearMatchedStr() {
        return yearMatchedStr;
    }

    public void setYearMatchedStr(String yearMatchedStr) {
        this.yearMatchedStr = yearMatchedStr;
    }

    public String getMonthMatchedStr() {
        return monthMatchedStr;
    }

    public void setMonthMatchedStr(String monthMatchedStr) {
        this.monthMatchedStr = monthMatchedStr;
    }

    public String getDayMatchedStr() {
        return dayMatchedStr;
    }

    public void setDayMatchedStr(String dayMatchedStr) {
        this.dayMatchedStr = dayMatchedStr;
    }

    public String getStartTimeMatchedStr() {
        return startTimeMatchedStr;
    }

    public void setStartTimeMatchedStr(String startTimeMatchedStr) {
        this.startTimeMatchedStr = startTimeMatchedStr;
    }

    public String getEndTimeMatchedStr() {
        return endTimeMatchedStr;
    }

    public void setEndTimeMatchedStr(String endTimeMatchedStr) {
        this.endTimeMatchedStr = endTimeMatchedStr;
    }

    public String getResultStr() {
        return resultStr;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean isMatched) {
        this.isMatched = isMatched;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public Date getStartDate(){

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,this.year == -1? SmartAnalyzeTimeUtil.curYearInt:this.year);
        calendar.set(Calendar.MONTH,this.month==-1? SmartAnalyzeTimeUtil.curMonthInt:this.month);

        if (dayInMonth == -1){//默认值
            //检查weekInMonth和dayInWeek
            if(weekInMonth != -1) {
                calendar.set(Calendar.WEEK_OF_MONTH, weekInMonth);
                calendar.set(Calendar.DAY_OF_WEEK,dayInWeek);
            }else{
                //默认值
                calendar.set(Calendar.DATE,SmartAnalyzeTimeUtil.curDayInMonth);
            }

        }else{
            calendar.set(Calendar.DATE,dayInMonth);
        }

        calendar.set(Calendar.HOUR_OF_DAY,getStartHour());
        calendar.set(Calendar.MINUTE,getStartMinute());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    public Date getEndDate(){
        if (endMinute ==-1 && endHour ==-1){
            return null;
        }

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,this.year == -1? SmartAnalyzeTimeUtil.curYearInt:this.year);
        calendar.set(Calendar.MONTH,this.month==-1? SmartAnalyzeTimeUtil.curMonthInt:this.month);

        if (dayInMonth == -1){//默认值
            //检查weekInMonth和dayInWeek
            if(weekInMonth != -1) {
                calendar.set(Calendar.WEEK_OF_MONTH, weekInMonth);
                calendar.set(Calendar.DAY_OF_WEEK,dayInWeek);
            }else{
                //默认值
                calendar.set(Calendar.DATE, SmartAnalyzeTimeUtil.curDayInMonth);
            }

        }else{
            calendar.set(Calendar.DATE,dayInMonth);
        }

        calendar.set(Calendar.HOUR_OF_DAY,getEndHour());
        calendar.set(Calendar.MINUTE,getEndMinute());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }

    public String getTargetStr(){
        int index=resultStr.lastIndexOf("]]");
        if(index<0){
            return resultStr;
        }else{
            return resultStr.substring(index+2);
        }
    }
    public static void main(String[] args) {

//        Calendar calendar=Calendar.getInstance();
//        System.out.println(calendar.getMinimalDaysInFirstWeek());
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//        calendar.set(Calendar.YEAR,2015);
//        calendar.set(Calendar.MONTH,2);
//        calendar.set(Calendar.WEEK_OF_MONTH,1);
//        calendar.set(Calendar.DAY_OF_WEEK,8);
//        System.out.println(DateUtil.getFormatString("yyyy-MM-dd",calendar.getTime()));
    }
}
