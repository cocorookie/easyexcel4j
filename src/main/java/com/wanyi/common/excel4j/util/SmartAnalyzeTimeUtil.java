package com.wanyi.common.excel4j.util;

import com.wanyi.common.excel4j.pojo.TextDateDO;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cocomao on 15/10/19.
 */
public class SmartAnalyzeTimeUtil {
    static Calendar calendar=Calendar.getInstance();
    static long initTime=0;
    static long initTimeMargin=0;
    static String curYearStr= "2015";
    public static int curYearInt=2015;
    public static int curMonthInt = 2;
    public static int curWeekInMonth = 3;
    public static int curDayInMonth= 18;
    public static int curDayInWeek = Calendar.MONDAY;//Ĭ����һ
    public static int curHourInDay= 0;
    public static int curMinute=0;
    public static void init(){
        long curTime=System.currentTimeMillis();
        if (curTime-initTime > 24 * 60 * 60 *1000 || DateUtil.getMargin(curTime) <= initTimeMargin){
            calendar.setTime(new Date());
            curYearInt=calendar.get(Calendar.YEAR);
            curMonthInt=calendar.get(Calendar.MONTH);
            curDayInMonth=calendar.get(Calendar.DATE);
            curWeekInMonth=calendar.get(Calendar.WEEK_OF_MONTH);
            curHourInDay = calendar.get(Calendar.HOUR_OF_DAY);
            curMinute=calendar.get(Calendar.MINUTE);
            curYearStr=String.valueOf(curYearInt);
            initMap();
            initTime=curTime;
            initTimeMargin=DateUtil.getMargin(initTime);
        }
    }
    static String exactDateRegx="((\\d{2,4})?([-/\\.])?(\\d{1,2})([-/\\.])(\\d{1,2}))";

    static String yearRegx="([ǰȥ������]|(\\d){2,4}|[һ�����������߰˾���]{2,4})��";
    static String yearRegx1 = "[������]��";
    static String yearRegx2 = "(\\d){2,4}";
    static String yearRegx3 = "[һ�����������߰˾���]{2,4}��";

    static String lunarDateRegx1 = "(ũ��|����)([һ�����������߰˾�ʮ����0-9]{1,2})��(��[һ�����������߰˾�ʮ]|[һ�����������߰˾�ʮ]{2,3}|\\d{1,2})(��|��)?";
    static String lunarDateRegx2 = "([����])��(��[һ�����������߰˾�ʮ]|[һ�����������߰˾�ʮ]{2,3}|\\d{1,2})(��|��)?";


    static String monthRegx="(((���|�ϸ�|�¸�|[������])|[һ�����������߰˾�ʮ0-9]{1,2})��)";
    static String monthRegx1="((���|�ϸ�|�¸�|[������])��)";
    static String monthRegx2="(([һ�����������߰˾�ʮ0-9]{1,2})��)";

    static String weekRegx="((���|�ϸ�|�¸�|����|����|[��������])?(��|���|����)([һ����������������ĩ1-7]))";

    static String dateRegx="((([һ�����������߰˾�ʮ]{1,3}|\\d{1,2})(��|��))|(����|����|����|����|ǰ��|��ǰ��|�����))";
    static String dateRegx1="(([һ�����������߰˾�ʮ]{1,3}|\\d{1,2})(��|��))";
    static String dateRegx2="(����|����|����|����|ǰ��|��ǰ��|�����)";



    static String timeRegx1="(�賿|����|����|����|����|����)";
    static String timeRegx2="((([0-9]{1,2}|[һ�����������߰˾�ʮ]{1,3})(��|ʱ))((��|һ��|����)|((([0-9]{1,2}|[һ�����������߰˾�ʮ]{1,3})��?)?)))";
    static String timeRegx3="(([0-9]{1,2})([:��])([0-9]{2})([:��])?)([0-9]{2})?";
    static String timeRegx4="(([һ�����������߰˾�ʮ]{1,3}|[\\d]{1,2})(��)?Сʱ(��|֮��))";
    static Pattern exactPattern=Pattern.compile(exactDateRegx);
    static Pattern yearPattern=Pattern.compile(yearRegx);
    static Pattern yearPattern1=Pattern.compile(yearRegx1);
    static Pattern yearPattern2=Pattern.compile(yearRegx2);
    static Pattern yearPattern3=Pattern.compile(yearRegx3);

    static Pattern lunarPattern1=Pattern.compile(lunarDateRegx1);
    static Pattern lunarPattern2=Pattern.compile(lunarDateRegx2);

    static Pattern monthPattern=Pattern.compile(monthRegx);
    static Pattern monthPattern1=Pattern.compile(monthRegx1);
    static Pattern monthPattern2=Pattern.compile(monthRegx2);
    static Pattern weekPattern=Pattern.compile(weekRegx);
    static Pattern datePattern=Pattern.compile(dateRegx);
    static Pattern datePattern1=Pattern.compile(dateRegx1);
    static Pattern datePattern2=Pattern.compile(dateRegx2);
    static Pattern timePattern1=Pattern.compile(timeRegx1);
    static Pattern timePattern2=Pattern.compile(timeRegx2);
    static Pattern timePattern3=Pattern.compile(timeRegx3);
    static Pattern timePattern4=Pattern.compile(timeRegx4);

    static Map<String,Integer> ch2Num=new HashMap<String,Integer>();
    static Map<String,Integer> yearMap=new HashMap<String,Integer>();
    static Map<String,Integer> monthMap=new HashMap<String, Integer>();
    static Map<String,Integer> weekMap=new HashMap<String, Integer>();
    static Map<String,Integer> dateMap=new HashMap<String, Integer>();
    static Map<String,Integer> timeMap=new HashMap<String, Integer>();

    private static void initMap(){
        yearMap.put("����",curYearInt);
        yearMap.put("����",curYearInt+1);
        yearMap.put("����",curYearInt+2);
        yearMap.put("ȥ��",curYearInt-1);
        yearMap.put("ǰ��",curYearInt-2);

        monthMap.put("����",curMonthInt);
        monthMap.put("����",curMonthInt+1);
        monthMap.put("����",curMonthInt-1);
        monthMap.put("�����",curMonthInt);
        monthMap.put("�¸���",curMonthInt+1);
        monthMap.put("�ϸ���",curMonthInt-1);

        weekMap.put("��",curWeekInMonth);
        weekMap.put("��",curWeekInMonth);
        weekMap.put("��",curWeekInMonth+1);
        weekMap.put("��",curWeekInMonth-1);
        weekMap.put("���",curWeekInMonth);
        weekMap.put("�¸�",curWeekInMonth+1);
        weekMap.put("�ϸ�",curWeekInMonth-1);
        weekMap.put("����",curWeekInMonth+2);
        weekMap.put("����",curWeekInMonth-2);
        weekMap.put(null,curWeekInMonth);


        dateMap.put("����",curDayInMonth);
        dateMap.put("����",curDayInMonth+1);
        dateMap.put("����",curDayInMonth+2);
        dateMap.put("�����",curDayInMonth+3);
        dateMap.put("����",curDayInMonth-1);
        dateMap.put("ǰ��",curDayInMonth-2);
        dateMap.put("��ǰ��",curDayInMonth-3);

    }
    static {
        ch2Num.put("��",0);
        ch2Num.put("һ",1);
        ch2Num.put("��",2);
        ch2Num.put("��",3);
        ch2Num.put("��",4);
        ch2Num.put("��",5);
        ch2Num.put("��",6);
        ch2Num.put("��",7);
        ch2Num.put("��",8);
        ch2Num.put("��",9);
        ch2Num.put("ʮ",10);

        ch2Num.put("ʮһ",11);
        ch2Num.put("ʮ��",12);
        ch2Num.put("ʮ��",13);
        ch2Num.put("ʮ��",14);
        ch2Num.put("ʮ��",15);
        ch2Num.put("ʮ��",16);
        ch2Num.put("ʮ��",17);
        ch2Num.put("ʮ��",18);
        ch2Num.put("ʮ��",19);

        ch2Num.put("��ʮ",20);
        ch2Num.put("��ʮһ",21);
        ch2Num.put("��ʮ��",22);
        ch2Num.put("��ʮ��",23);
        ch2Num.put("��ʮ��",24);
        ch2Num.put("��ʮ��",25);
        ch2Num.put("��ʮ��",26);
        ch2Num.put("��ʮ��",27);
        ch2Num.put("��ʮ��",28);
        ch2Num.put("��ʮ��",29);

        ch2Num.put("��ʮ",30);
        ch2Num.put("��ʮһ",31);
        ch2Num.put("��ʮ��",32);
        ch2Num.put("��ʮ��",33);
        ch2Num.put("��ʮ��",34);
        ch2Num.put("��ʮ��",35);
        ch2Num.put("��ʮ��",36);
        ch2Num.put("��ʮ��",37);
        ch2Num.put("��ʮ��",38);
        ch2Num.put("��ʮ��",39);

        ch2Num.put("��ʮ",40);
        ch2Num.put("��ʮһ",41);
        ch2Num.put("��ʮ��",42);
        ch2Num.put("��ʮ��",43);
        ch2Num.put("��ʮ��",44);
        ch2Num.put("��ʮ��",45);
        ch2Num.put("��ʮ��",46);
        ch2Num.put("��ʮ��",47);
        ch2Num.put("��ʮ��",48);
        ch2Num.put("��ʮ��",49);

        ch2Num.put("��ʮ",50);
        ch2Num.put("��ʮһ",51);
        ch2Num.put("��ʮ��",52);
        ch2Num.put("��ʮ��",53);
        ch2Num.put("��ʮ��",54);
        ch2Num.put("��ʮ��",55);
        ch2Num.put("��ʮ��",56);
        ch2Num.put("��ʮ��",57);
        ch2Num.put("��ʮ��",58);
        ch2Num.put("��ʮ��",59);

        ch2Num.put("��",1);
        ch2Num.put("��",12);

        weekMap.put("һ",2);
        weekMap.put("��",3);
        weekMap.put("��",4);
        weekMap.put("��",5);
        weekMap.put("��",6);
        weekMap.put("��",7);
        weekMap.put("��",1);
        weekMap.put("��",1);
        weekMap.put("��",1);
        weekMap.put("ĩ",7);

        dateMap.put("��һ",1);
        dateMap.put("����",2);
        dateMap.put("����",3);
        dateMap.put("����",4);
        dateMap.put("����",5);
        dateMap.put("����",6);
        dateMap.put("����",7);
        dateMap.put("����",8);
        dateMap.put("����",9);
        dateMap.put("��ʮ",10);

        dateMap.put("ʮһ",11);
        dateMap.put("ʮ��",12);
        dateMap.put("ʮ��",13);
        dateMap.put("ʮ��",14);
        dateMap.put("ʮ��",15);
        dateMap.put("ʮ��",16);
        dateMap.put("ʮ��",17);
        dateMap.put("ʮ��",18);
        dateMap.put("ʮ��",19);

        dateMap.put("��ʮ",20);
        dateMap.put("��ʮһ",21);
        dateMap.put("��ʮ��",22);
        dateMap.put("��ʮ��",23);
        dateMap.put("��ʮ��",24);
        dateMap.put("��ʮ��",25);
        dateMap.put("��ʮ��",26);
        dateMap.put("��ʮ��",27);
        dateMap.put("��ʮ��",28);
        dateMap.put("��ʮ��",29);

        dateMap.put("��ʮ",30);
        dateMap.put("��ʮһ",31);

        timeMap.put("�賿",0);
        timeMap.put("����",8);
        timeMap.put("����",8);
        timeMap.put("����",12);
        timeMap.put("����",14);
        timeMap.put("����",20);
    }

    //(ũ��|����)([һ�����������߰˾�ʮ����0-9]{1,2})��(��[һ�����������߰˾�ʮ]|[һ�����������߰˾�ʮ]{2,3}|\d{1,2})(��|��)?
    public static String getLunarDate1(String text,TextDateDO date){
        Matcher matcher=lunarPattern1.matcher(text);
        if (matcher.find()){
            String matchedMonth=matcher.group(2);
            String matchedDate=matcher.group(3);
            Integer month=ch2Num.get(matchedMonth);
            if (month==null){
                try {
                    month = Integer.valueOf(matchedMonth);
                }catch (Exception ex){
                    return  null;
                }
            }
            date.setMonth(month);
            date.setMonthMatchedStr(matchedMonth);
            text=text.replace(matchedMonth,"[["+matchedMonth+"]]");
            Integer dateInMonth=dateMap.get(matchedDate);
            if (dateInMonth==null){
                try {
                    dateInMonth = Integer.valueOf(matchedDate);
                }catch (Exception ex){
                    return null;
                }
            }
            date.setDayInMonth(dateInMonth);
            date.setDayMatchedStr(matchedDate);
            text=text.replace(matchedDate,"[["+matchedDate+"]]");
            return text;
        }
        return null;
    }
    //([����])��(��[һ�����������߰˾�ʮ]|[һ�����������߰˾�ʮ]{2,3}|\d{1,2})(��|��)?
    public static String getLunarDate2(String text,TextDateDO date){
        Matcher matcher=lunarPattern2.matcher(text);
        if (matcher.find()){
            String matchedMonth=matcher.group(1);
            String matchedDate=matcher.group(2);
            Integer month=ch2Num.get(matchedMonth);
            if (month==null){
                try {
                    month = Integer.valueOf(matchedMonth);
                }catch (Exception ex){
                    return  null;
                }
            }
            date.setMonth(month);
            date.setMonthMatchedStr(matchedMonth);
            text=text.replace(matchedMonth,"[["+matchedMonth+"]]");
            Integer dateInMonth=dateMap.get(matchedDate);
            if (dateInMonth==null){
                try {
                    dateInMonth = Integer.valueOf(matchedDate);
                }catch (Exception ex){
                    return null;
                }
            }
            date.setDayInMonth(dateInMonth);
            date.setDayMatchedStr(matchedDate);
            text=text.replace(matchedDate,"[["+matchedDate+"]]");
            return text;
        }
        return null;
    }
    /**
     * ��ȡСʱ�ͷ���
     * @param text
     * @param date
     * @return
     */
    public static String getHourAndMinute(String text,TextDateDO date){
        Matcher matcher=timePattern1.matcher(text);
        boolean isMorning=true;
        Integer hour=null;
        String startTimeStr=null;
        String endTimeStr=null;
        StringBuilder matchedText=new StringBuilder();
        if (matcher.find()){//����ǿ��Ժͺ���Ļ��ʹ�õ�
            matchedText.append(matcher.group());
            hour=timeMap.get(matcher.group());
            if (hour >= 12){
                isMorning=false;
            }
            text=text.replace(matchedText.toString(),"[["+matchedText.toString()+"]]");
            matcher=timePattern2.matcher(text);
            if (matcher.find()){
                startTimeStr=getHourAndMiniteInPatten2(matcher, isMorning, date, true);
                if (matcher.find()){//��ȥ��һ�Σ���Ϊ�п�����12��12�ֵ�14��34�ֵ��������
                    endTimeStr=getHourAndMiniteInPatten2(matcher, isMorning, date, false);
                }
                if (startTimeStr!=null) {
                    text=text.replace(startTimeStr, "[[" + startTimeStr +"]]");
                    matchedText.append(startTimeStr);
                    date.setStartTimeMatchedStr(matchedText.toString());
                }
                if (endTimeStr!=null){
                    text=text.replace(endTimeStr, "[[" + endTimeStr +"]]");
                    matchedText.append(endTimeStr);
                    date.setEndTimeMatchedStr(matchedText.toString());
                }

                if (hour == 0 ){//�賿����������
                    if (date.getStartHour() < curHourInDay ){
                        date.setStartHour(date.getStartHour() + 24);
                    }
                }
                if (hour == 20){
                    if (date.getStartHour() < curHourInDay && date.getStartHour() < 18){
                        date.setStartHour(date.getStartHour() + 12);
                    }
                }


                return text;
            }
            matcher=timePattern3.matcher(text);
            if (matcher.find()){
                startTimeStr=getHourAndMiniteInPatten3(matcher, isMorning, date, true);
                if (matcher.find()){
                    endTimeStr=getHourAndMiniteInPatten3(matcher, isMorning, date, false);
                }
                if (startTimeStr!=null) {
                    text=text.replace(startTimeStr, "[[" + startTimeStr +"]]");
                    matchedText.append(startTimeStr);
                    date.setStartTimeMatchedStr(matchedText.toString());
                }
                if (endTimeStr!=null){
                    text=text.replace(endTimeStr, "[[" + endTimeStr +"]]");
                    matchedText.append(endTimeStr);
                    date.setEndTimeMatchedStr(matchedText.toString());
                }
                if (hour == 0 ){//�賿����������
                    if (date.getStartHour() < curHourInDay ){
                        date.setStartHour(date.getStartHour() + 24);
                    }
                }
                if (hour == 20){
                    if (date.getStartHour() < curHourInDay && date.getStartHour() < 18){
                        date.setStartHour(date.getStartHour() + 12);
                    }
                }
                return text;
            }
            //����ߵ����˵��û���ҵ�patter2��pattern3,��ôֱ�ӷ���
            if (hour==0) {
                date.setStartHour(24);
            }else {
                date.setStartHour(hour);
            }
            date.setStartTimeMatchedStr(matchedText.toString());
            return text;
        }
        matcher=timePattern2.matcher(text);
        if (matcher.find()){
            startTimeStr=getHourAndMiniteInPatten2(matcher,isMorning,date,true);
            if (matcher.find()){//��ȥ��һ�Σ���Ϊ�п�����12��12�ֵ�14��34�ֵ��������
                endTimeStr=getHourAndMiniteInPatten2(matcher,isMorning,date,false);
            }
            if (startTimeStr!=null) {
                text=text.replace(startTimeStr, "[[" + startTimeStr +"]]");
                matchedText.append(startTimeStr);
                date.setStartTimeMatchedStr(matchedText.toString());
            }
            if (endTimeStr!=null){
                text=text.replace(endTimeStr, "[[" + endTimeStr +"]]");
                matchedText.append(endTimeStr);
                date.setEndTimeMatchedStr(matchedText.toString());
            }
            return text;
        }
        matcher=timePattern3.matcher(text);
        if (matcher.find()){
            startTimeStr=getHourAndMiniteInPatten3(matcher,isMorning,date,true);
            if (matcher.find()){
                endTimeStr=getHourAndMiniteInPatten3(matcher,isMorning,date,false);
            }
            if (startTimeStr!=null) {
                text=text.replace(startTimeStr, "[[" + startTimeStr +"]]");
                matchedText.append(startTimeStr);
                date.setStartTimeMatchedStr(matchedText.toString());
            }
            if (endTimeStr!=null){
                text=text.replace(endTimeStr, "[[" + endTimeStr +"]]");
                matchedText.append(endTimeStr);
                date.setEndTimeMatchedStr(matchedText.toString());
            }
            return text;
        }
        matcher=timePattern4.matcher(text);
        if (matcher.find()){
            String matchedTime=matcher.group();
            text=text.replace(matchedTime, "[[" + matchedTime +"]]");
            String matchedHour=matcher.group(2);
            Integer hourInt=ch2Num.get(matchedHour);
            if (hourInt == null){
                try{
                    hourInt=Integer.valueOf(matchedHour);
                    date.setStartHour(curHourInDay+hourInt);
                    date.setStartMinute(curMinute);
                }catch (Exception ex){
                    //���������쳣�����߼���
                }
            }else {
                date.setStartHour(curHourInDay+hourInt);
                date.setStartMinute(curMinute);
            }
            return text;
        }
        return null;
    }

    private static String getHourAndMiniteInPatten3(Matcher matcher,Boolean isMorning, TextDateDO date, boolean isStart){
        String hourStr=matcher.group(2);
        String minuteStr=matcher.group(4);
        Integer hour=Integer.valueOf(hourStr);
        if (!isMorning && hour<12){
            hour+=12;
        }
        Integer minute=Integer.valueOf(minuteStr);
        if (isStart){
            date.setStartHour(hour);
            date.setStartMinute(minute);
        }else{
            date.setEndHour(hour);
            date.setEndMinute(minute);
        }
        return matcher.group();
    }
    private static String getHourAndMiniteInPatten2(Matcher matcher,Boolean isMorning,TextDateDO date,boolean isStart){
        String hourStr=matcher.group(3);
        String halfHourStr=matcher.group(6);
        String minuteStr=matcher.group(9);//�п���Ϊnull
        Integer hour=ch2Num.get(hourStr);
        Integer minute=null;
        if (hour == null){
            hour = Integer.valueOf(hourStr);//˵��������
        }
        if (!isMorning && hour<12){
            hour+=12;//�����24Сʱ��
        }
        if ("��".equals(halfHourStr)){
            minute=30;
        }else if("һ��".equals(halfHourStr)){
            minute=15;
        }else if("����".equals(halfHourStr)){
            minute=45;
        }else{
            if(minuteStr == null){
                //Ĭ��ֵ
            }else{
                minute=ch2Num.get(minuteStr);
                if (minute == null){
                    minute = Integer.valueOf(minuteStr);//˵��������
                }
            }
        }
        if (isStart){
            date.setStartHour(hour);
            date.setStartMinute(minute==null ? 0:minute);
        }else{
            date.setEndHour(hour);
            date.setEndMinute(minute==null?0:minute);
        }
        return matcher.group();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        DateStrAnalizeUtil.init();
//        List<String> strList=new ArrayList<String>();
////        strList.add("�����������������ĵ���ʮ����Ҫ�㱼��");
////        strList.add("����4��3��4:30��5:30��Ҫ�㱼");
////        strList.add("�¸���3����������Ȳ�");
////        strList.add("����������ȥ����Ӱ");
////        strList.add("��������������ȥ�Է�");
////        strList.add("��������18��40����");
////        strList.add("20160318 18:00��Ҫ�Է�");
//        strList.add("һ��Сʱ����Ϫʪ�ر���׼ʱ��");
////        strList.add("������һ����2:00���ҼҳԷ�");
//        for (String str:strList){
//            TextDateDO textDateDO=getDateByText(str);
//            System.out.println(textDateDO.getResultStr()+":"+DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss",textDateDO.getStartDate())+"��"+DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss",textDateDO.getEndDate()));
//        }
//
//        String text="15��Сʱ��";
//        Matcher matcher=timePattern4.matcher(text);
//        if (matcher.find()){
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println(i+":"+matcher.group(i));
//            }
//        }
        SmartAnalyzeTimeUtil.init();
        String text="07��04�� ~ 10��01�� ÿ��20:10-22:00";
        TextDateDO textDateDO=SmartAnalyzeTimeUtil.getDateByText(text);
        System.out.println(textDateDO.getTargetStr());
        System.out.println(textDateDO.getStartDate());
        System.out.println(textDateDO.getEndDate());


    }


    public static TextDateDO getDateByText(String text){
        TextDateDO textDateDO=new TextDateDO();
        String textRes=null;
        textRes=getExactDate(text,textDateDO);
        if (textRes != null){//˵��ƥ�䵽��
            textDateDO.setMatched(true);
            text=textRes;
            if (textDateDO.getYear() == -1){//���û����
                textRes=getYear(text,textDateDO);
                if(textRes!=null ){
                    text=textRes;
                }
            }
        }else {
            textRes = getYear(text, textDateDO);
            if (textRes != null) {
                textDateDO.setMatched(true);
                text = textRes;
            }
            textRes = getLunarDate1(text,textDateDO);
            if (textRes != null) {
                textDateDO.setMatched(true);
                text = textRes;
            }else {
                textRes = getLunarDate2(text, textDateDO);
                if (textRes != null) {
                    textDateDO.setMatched(true);
                    text = textRes;
                } else {
                    textRes = getMonth(text, textDateDO);
                    if (textRes != null) {
                        textDateDO.setMatched(true);
                        text = textRes;
                    }
                    textRes = getDateByWeek(text, textDateDO);
                    if (textRes != null) {
                        textDateDO.setMatched(true);
                        text = textRes;
                    } else {
                        textRes = getDateByDate(text, textDateDO);
                        if (textRes != null) {
                            textDateDO.setMatched(true);
                            text = textRes;
                        }
                    }
                }
            }
        }
        textRes = getHourAndMinute(text, textDateDO);
        if (textRes != null) {
            textDateDO.setMatched(true);
            text = textRes;
        }
        textDateDO.setResultStr(text);
        return textDateDO;

    }
    public static String getExactDate(String text,TextDateDO date){
        Matcher matcher=exactPattern.matcher(text);
        if (matcher.find()){
            String matchedText=matcher.group();

            if (text.contains(":"+matchedText+":")){
                return null;
            }

            String matchedYear=matcher.group(2);//�п���Ϊ��
            String matchedMonth=matcher.group(4);
            String matchedDate=matcher.group(6);
            if (matchedYear!=null){
                String finalNumYear=matchedYear.length() == 4? matchedYear: curYearStr.substring(0,4-matchedYear.length()) + matchedYear;
                date.setYear(Integer.valueOf(finalNumYear));
                date.setYearMatchedStr(matchedYear);
            }
            int matchedMonthInt=Integer.valueOf(matchedMonth);
            if(matchedMonthInt < 1 || matchedMonthInt > 12 ){
                return null;
            }

            date.setMonth(Integer.valueOf(matchedMonth) - 1);
            date.setMonthMatchedStr(matchedMonth);


            int matchedDateInt=Integer.valueOf(matchedDate);
            if (matchedDateInt < 1 || matchedDateInt > 31){
                return null;
            }
            date.setDayInMonth(Integer.valueOf(matchedDate));
            date.setDayMatchedStr(matchedDate);
            text=text.replace(matchedText,"[["+matchedText+"]]");
            return text;
        }
        return null;
    }
    /**
     * ��ȡ����ص�����
     * @param text
     * @return
     */
    private static String getDateByWeek(String text,TextDateDO date){
        Matcher matcher=weekPattern.matcher(text);
        if(matcher.find()) {
            String matchText = matcher.group();
            String prefixMatch=matcher.group(2);
            Integer weekInMonth=weekMap.get(prefixMatch);
            date.setWeekInMonth(weekInMonth);

            String suffixMatch=matcher.group(4);
            Integer dayInWeek = weekMap.get(suffixMatch);
            if (dayInWeek == null){
                dayInWeek=Integer.valueOf(suffixMatch);
            }
            date.setDayInWeek(dayInWeek);
            if (curDayInWeek == Calendar.SUNDAY && dayInWeek!=Calendar.SUNDAY){//���������գ�Ŀ�����ڲ�������
                date.setWeekInMonth(date.getWeekInMonth() - 1);
            }else if (curDayInWeek != Calendar.SUNDAY && dayInWeek == Calendar.SUNDAY){
                date.setWeekInMonth(date.getWeekInMonth() + 1);
            }
            date.setDayMatchedStr(matchText);
            return text.replace(matchText,"[["+matchText+"]]");
        }
        return null;
    }

    /**
     * ��ȡ����
     * @param text
     * @return
     */
    private static String getDateByDate(String text,TextDateDO date){
        Matcher matcher=datePattern.matcher(text);
        if(matcher.find()) {
            String matchText = matcher.group();
            matcher=datePattern1.matcher(matchText);
            if (matcher.find()){
                String chsDateInMonth=matcher.group(2);
                Integer dateInMonth=ch2Num.get(chsDateInMonth);
                if(dateInMonth == null){
                    try {
                        dateInMonth=Integer.valueOf(chsDateInMonth);
                    }catch (Exception ex){
                        return null;
                    }
                }
                date.setDayInMonth(dateInMonth);
                date.setDayMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");

            }
            matcher=datePattern2.matcher(matchText);
            if(matcher.find()){
                String chsDateInMonth=matcher.group();
                Integer dateInMonth=dateMap.get(chsDateInMonth);
                if(dateInMonth == null){
                    try {
                        dateInMonth=Integer.valueOf(chsDateInMonth);
                    }catch (Exception ex){
                        return null;
                    }
                }
                date.setDayInMonth(dateInMonth);
                date.setDayMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");

            }

        }
        return null;
    }

    /**
     * ���ַ����н�������ݺͶ�Ӧƥ����ַ���
     * @param text ���������ַ���
     * @param dateDO �������
     */
    private static String getYear(String text, TextDateDO dateDO){
        Matcher matcher=yearPattern.matcher(text);
        if(matcher.find()){
            String matchText=matcher.group();
            //�ж�����һ��
            matcher=yearPattern1.matcher(matchText);
            if (matcher.find()){
                Integer year=yearMap.get(matchText);
                dateDO.setYear(year==null?curYearInt:year);
                dateDO.setYearMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");
            }
            matcher=yearPattern2.matcher(matchText);
            if (matcher.find()){
                String numYear=matchText.substring(0,matchText.indexOf("��"));
                String finalNumYear=numYear.length() == 4? numYear: curYearStr.substring(0,4-numYear.length()) + numYear;
                dateDO.setYear(Integer.valueOf(finalNumYear));
                dateDO.setYearMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");
            }
            matcher=yearPattern3.matcher(matchText);
            if (matcher.find()){
                String chsYear=matchText.substring(0,matchText.indexOf("��"));
                StringBuilder sb=new StringBuilder();
                for (char ch:chsYear.toCharArray()){
                    sb.append(ch2Num.get(String.valueOf(ch)));
                }
                String numYear=sb.toString();
                String finalNumYear=numYear.length() == 4? numYear: curYearStr.substring(0,4-numYear.length()) + numYear;
                dateDO.setYear(Integer.valueOf(finalNumYear));
                dateDO.setYearMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");
            }

        }
        return null;
    }

    /**
     * j���ַ����н������·�
     * @param text Ҫ�������ַ���
     * @param date �������
     * @return
     */
    private static String getMonth(String text,TextDateDO date){
        Matcher matcher=monthPattern.matcher(text);
        if(matcher.find()) {
            String matchText = matcher.group();
            matcher=monthPattern1.matcher(matchText);
            if(matcher.matches()){
                Integer numMonth=monthMap.get(matchText);
                date.setMonth(numMonth==null ? curMonthInt : numMonth);
                date.setMonthMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");
            }
            matcher=monthPattern2.matcher(matchText);
            if(matcher.matches()){
                String chsMonth=matchText.substring(0,matchText.indexOf("��"));

                Integer month=ch2Num.get(String.valueOf(chsMonth));
                if (month==null){
                    month=Integer.valueOf(chsMonth);
                }
                date.setMonth(month-1);
                date.setMonthMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");
            }

        }
        return null;
    }

}
