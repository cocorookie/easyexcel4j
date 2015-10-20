package com.wanyi.common.excel4j.pojo;

import com.wanyi.common.excel4j.util.SmartAnalyzeTimeUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cocomao on 15/10/19.
 */
public class TextDateDO {

    /**
     * ���ص�������match���ģ�����˵��Ĭ��ֵ�����죩
     */
    private boolean isMatched=false;

    /**
     * ���
     */
    private int year=-1;
    /**
     * ƥ�������ַ���
     */
    private String yearMatchedStr;
    /**
     * �·�
     */
    private int month=-1;
    /**
     * ƥ����·��ַ���
     */
    private String monthMatchedStr;
    /**
     * ���µڼ���
     */
    private int dayInMonth = -1;
    /**
     * ���µڼ���
     */
    private int weekInMonth = -1;
    /**
     * ���ܵڼ���
     */
    private int dayInWeek = -1;
    /**
     * ��������ƥ����ַ���
     */
    private String dayMatchedStr;
    /**
     * ��ʼʱ��Сʱ
     */
    private int startHour=0;
    /**
     * ��ʼʱ�����
     */
    private int startMinute = 0;
    /**
     * ����ʱ��Сʱ
     */
    private int endHour=-1;
    /**
     * ����ʱ�����
     */
    private int endMinute = -1;
    /**
     * ��ʼʱ��ƥ����ַ���
     */
    private String startTimeMatchedStr;
    /**
     * ����ʱ��ƥ����ַ���
     */
    private String endTimeMatchedStr;
    /**
     * ƥ�䴦��֮����ַ���
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

        if (dayInMonth == -1){//Ĭ��ֵ
            //���weekInMonth��dayInWeek
            if(weekInMonth != -1) {
                calendar.set(Calendar.WEEK_OF_MONTH, weekInMonth);
                calendar.set(Calendar.DAY_OF_WEEK,dayInWeek);
            }else{
                //Ĭ��ֵ
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

        if (dayInMonth == -1){//Ĭ��ֵ
            //���weekInMonth��dayInWeek
            if(weekInMonth != -1) {
                calendar.set(Calendar.WEEK_OF_MONTH, weekInMonth);
                calendar.set(Calendar.DAY_OF_WEEK,dayInWeek);
            }else{
                //Ĭ��ֵ
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
