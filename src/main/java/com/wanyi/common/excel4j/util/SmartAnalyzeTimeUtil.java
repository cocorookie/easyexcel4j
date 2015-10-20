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
    public static int curDayInWeek = Calendar.MONDAY;//默认周一
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

    static String yearRegx="([前去今明后]|(\\d){2,4}|[一二三四五六七八九零]{2,4})年";
    static String yearRegx1 = "[今明后]年";
    static String yearRegx2 = "(\\d){2,4}";
    static String yearRegx3 = "[一二三四五六七八九零]{2,4}年";

    static String lunarDateRegx1 = "(农历|阴历)([一二三四五六七八九十正腊0-9]{1,2})月(初[一二三四五六七八九十]|[一二三四五六七八九十]{2,3}|\\d{1,2})(日|号)?";
    static String lunarDateRegx2 = "([正腊])月(初[一二三四五六七八九十]|[一二三四五六七八九十]{2,3}|\\d{1,2})(日|号)?";


    static String monthRegx="(((这个|上个|下个|[本上下])|[一二三四五六七八九十0-9]{1,2})月)";
    static String monthRegx1="((这个|上个|下个|[本上下])月)";
    static String monthRegx2="(([一二三四五六七八九十0-9]{1,2})月)";

    static String weekRegx="((这个|上个|下个|下下|上上|[本下上这])?(周|礼拜|星期)([一二三四五六七日天末1-7]))";

    static String dateRegx="((([一二三四五六七八九十]{1,3}|\\d{1,2})(日|号))|(今天|昨天|明天|后天|前天|大前天|大后天))";
    static String dateRegx1="(([一二三四五六七八九十]{1,3}|\\d{1,2})(日|号))";
    static String dateRegx2="(今天|昨天|明天|后天|前天|大前天|大后天)";



    static String timeRegx1="(凌晨|早上|上午|中午|下午|晚上)";
    static String timeRegx2="((([0-9]{1,2}|[一二三四五六七八九十]{1,3})(点|时))((半|一刻|三刻)|((([0-9]{1,2}|[一二三四五六七八九十]{1,3})分?)?)))";
    static String timeRegx3="(([0-9]{1,2})([:：])([0-9]{2})([:：])?)([0-9]{2})?";
    static String timeRegx4="(([一二三四五六七八九十]{1,3}|[\\d]{1,2})(个)?小时(后|之后))";
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
        yearMap.put("今年",curYearInt);
        yearMap.put("明年",curYearInt+1);
        yearMap.put("后年",curYearInt+2);
        yearMap.put("去年",curYearInt-1);
        yearMap.put("前年",curYearInt-2);

        monthMap.put("本月",curMonthInt);
        monthMap.put("下月",curMonthInt+1);
        monthMap.put("上月",curMonthInt-1);
        monthMap.put("这个月",curMonthInt);
        monthMap.put("下个月",curMonthInt+1);
        monthMap.put("上个月",curMonthInt-1);

        weekMap.put("本",curWeekInMonth);
        weekMap.put("这",curWeekInMonth);
        weekMap.put("下",curWeekInMonth+1);
        weekMap.put("上",curWeekInMonth-1);
        weekMap.put("这个",curWeekInMonth);
        weekMap.put("下个",curWeekInMonth+1);
        weekMap.put("上个",curWeekInMonth-1);
        weekMap.put("下下",curWeekInMonth+2);
        weekMap.put("上上",curWeekInMonth-2);
        weekMap.put(null,curWeekInMonth);


        dateMap.put("今天",curDayInMonth);
        dateMap.put("明天",curDayInMonth+1);
        dateMap.put("后天",curDayInMonth+2);
        dateMap.put("大后天",curDayInMonth+3);
        dateMap.put("昨天",curDayInMonth-1);
        dateMap.put("前天",curDayInMonth-2);
        dateMap.put("大前天",curDayInMonth-3);

    }
    static {
        ch2Num.put("零",0);
        ch2Num.put("一",1);
        ch2Num.put("二",2);
        ch2Num.put("三",3);
        ch2Num.put("四",4);
        ch2Num.put("五",5);
        ch2Num.put("六",6);
        ch2Num.put("七",7);
        ch2Num.put("八",8);
        ch2Num.put("九",9);
        ch2Num.put("十",10);

        ch2Num.put("十一",11);
        ch2Num.put("十二",12);
        ch2Num.put("十三",13);
        ch2Num.put("十四",14);
        ch2Num.put("十五",15);
        ch2Num.put("十六",16);
        ch2Num.put("十七",17);
        ch2Num.put("十八",18);
        ch2Num.put("十九",19);

        ch2Num.put("二十",20);
        ch2Num.put("二十一",21);
        ch2Num.put("二十二",22);
        ch2Num.put("二十三",23);
        ch2Num.put("二十四",24);
        ch2Num.put("二十五",25);
        ch2Num.put("二十六",26);
        ch2Num.put("二十七",27);
        ch2Num.put("二十八",28);
        ch2Num.put("二十九",29);

        ch2Num.put("三十",30);
        ch2Num.put("三十一",31);
        ch2Num.put("三十二",32);
        ch2Num.put("三十三",33);
        ch2Num.put("三十四",34);
        ch2Num.put("三十五",35);
        ch2Num.put("三十六",36);
        ch2Num.put("三十七",37);
        ch2Num.put("三十八",38);
        ch2Num.put("三十九",39);

        ch2Num.put("四十",40);
        ch2Num.put("四十一",41);
        ch2Num.put("四十二",42);
        ch2Num.put("四十三",43);
        ch2Num.put("四十四",44);
        ch2Num.put("四十五",45);
        ch2Num.put("四十六",46);
        ch2Num.put("四十七",47);
        ch2Num.put("四十八",48);
        ch2Num.put("四十九",49);

        ch2Num.put("五十",50);
        ch2Num.put("五十一",51);
        ch2Num.put("五十二",52);
        ch2Num.put("五十三",53);
        ch2Num.put("五十四",54);
        ch2Num.put("五十五",55);
        ch2Num.put("五十六",56);
        ch2Num.put("五十七",57);
        ch2Num.put("五十八",58);
        ch2Num.put("五十九",59);

        ch2Num.put("正",1);
        ch2Num.put("腊",12);

        weekMap.put("一",2);
        weekMap.put("二",3);
        weekMap.put("三",4);
        weekMap.put("四",5);
        weekMap.put("五",6);
        weekMap.put("六",7);
        weekMap.put("七",1);
        weekMap.put("天",1);
        weekMap.put("日",1);
        weekMap.put("末",7);

        dateMap.put("初一",1);
        dateMap.put("初二",2);
        dateMap.put("初三",3);
        dateMap.put("初四",4);
        dateMap.put("初五",5);
        dateMap.put("初六",6);
        dateMap.put("初七",7);
        dateMap.put("初八",8);
        dateMap.put("初九",9);
        dateMap.put("初十",10);

        dateMap.put("十一",11);
        dateMap.put("十二",12);
        dateMap.put("十三",13);
        dateMap.put("十四",14);
        dateMap.put("十五",15);
        dateMap.put("十六",16);
        dateMap.put("十七",17);
        dateMap.put("十八",18);
        dateMap.put("十九",19);

        dateMap.put("二十",20);
        dateMap.put("二十一",21);
        dateMap.put("二十二",22);
        dateMap.put("二十三",23);
        dateMap.put("二十四",24);
        dateMap.put("二十五",25);
        dateMap.put("二十六",26);
        dateMap.put("二十七",27);
        dateMap.put("二十八",28);
        dateMap.put("二十九",29);

        dateMap.put("三十",30);
        dateMap.put("三十一",31);

        timeMap.put("凌晨",0);
        timeMap.put("早上",8);
        timeMap.put("上午",8);
        timeMap.put("中午",12);
        timeMap.put("下午",14);
        timeMap.put("晚上",20);
    }

    //(农历|阴历)([一二三四五六七八九十正腊0-9]{1,2})月(初[一二三四五六七八九十]|[一二三四五六七八九十]{2,3}|\d{1,2})(日|号)?
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
    //([正腊])月(初[一二三四五六七八九十]|[一二三四五六七八九十]{2,3}|\d{1,2})(日|号)?
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
     * 获取小时和分钟
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
        if (matcher.find()){//这个是可以和后面的混合使用的
            matchedText.append(matcher.group());
            hour=timeMap.get(matcher.group());
            if (hour >= 12){
                isMorning=false;
            }
            text=text.replace(matchedText.toString(),"[["+matchedText.toString()+"]]");
            matcher=timePattern2.matcher(text);
            if (matcher.find()){
                startTimeStr=getHourAndMiniteInPatten2(matcher, isMorning, date, true);
                if (matcher.find()){//再去找一次，因为有可能是12点12分到14点34分的这种组合
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

                if (hour == 0 ){//凌晨或者是晚上
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
                if (hour == 0 ){//凌晨或者是晚上
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
            //如果走到这里，说明没有找到patter2和pattern3,那么直接返回
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
            if (matcher.find()){//再去找一次，因为有可能是12点12分到14点34分的这种组合
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
                    //这里是用异常来做逻辑了
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
        String minuteStr=matcher.group(9);//有可能为null
        Integer hour=ch2Num.get(hourStr);
        Integer minute=null;
        if (hour == null){
            hour = Integer.valueOf(hourStr);//说明是数字
        }
        if (!isMorning && hour<12){
            hour+=12;//换算成24小时制
        }
        if ("半".equals(halfHourStr)){
            minute=30;
        }else if("一刻".equals(halfHourStr)){
            minute=15;
        }else if("三刻".equals(halfHourStr)){
            minute=45;
        }else{
            if(minuteStr == null){
                //默认值
            }else{
                minute=ch2Num.get(minuteStr);
                if (minute == null){
                    minute = Integer.valueOf(minuteStr);//说明是数字
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
////        strList.add("今年四月三号下午四点三十分我要裸奔！");
////        strList.add("今年4月3号4:30到5:30我要裸奔");
////        strList.add("下个月3号下午我想喝茶");
////        strList.add("下周四我想去看电影");
////        strList.add("下周日上午我想去吃饭");
////        strList.add("这个礼拜天18：40开会");
////        strList.add("20160318 18:00我要吃饭");
//        strList.add("一个小时后西溪湿地北门准时见");
////        strList.add("下下周一下午2:00来我家吃饭");
//        for (String str:strList){
//            TextDateDO textDateDO=getDateByText(str);
//            System.out.println(textDateDO.getResultStr()+":"+DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss",textDateDO.getStartDate())+"到"+DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss",textDateDO.getEndDate()));
//        }
//
//        String text="15个小时后";
//        Matcher matcher=timePattern4.matcher(text);
//        if (matcher.find()){
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println(i+":"+matcher.group(i));
//            }
//        }
        SmartAnalyzeTimeUtil.init();
        String text="07月04日 ~ 10月01日 每天20:10-22:00";
        TextDateDO textDateDO=SmartAnalyzeTimeUtil.getDateByText(text);
        System.out.println(textDateDO.getTargetStr());
        System.out.println(textDateDO.getStartDate());
        System.out.println(textDateDO.getEndDate());


    }


    public static TextDateDO getDateByText(String text){
        TextDateDO textDateDO=new TextDateDO();
        String textRes=null;
        textRes=getExactDate(text,textDateDO);
        if (textRes != null){//说明匹配到了
            textDateDO.setMatched(true);
            text=textRes;
            if (textDateDO.getYear() == -1){//年份没设置
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

            String matchedYear=matcher.group(2);//有可能为空
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
     * 获取周相关的日期
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
            if (curDayInWeek == Calendar.SUNDAY && dayInWeek!=Calendar.SUNDAY){//今天是周日，目标日期不是周日
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
     * 获取日期
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
     * 从字符串中解析出年份和对应匹配的字符串
     * @param text 被解析的字符串
     * @param dateDO 解析结果
     */
    private static String getYear(String text, TextDateDO dateDO){
        Matcher matcher=yearPattern.matcher(text);
        if(matcher.find()){
            String matchText=matcher.group();
            //判断是哪一种
            matcher=yearPattern1.matcher(matchText);
            if (matcher.find()){
                Integer year=yearMap.get(matchText);
                dateDO.setYear(year==null?curYearInt:year);
                dateDO.setYearMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");
            }
            matcher=yearPattern2.matcher(matchText);
            if (matcher.find()){
                String numYear=matchText.substring(0,matchText.indexOf("年"));
                String finalNumYear=numYear.length() == 4? numYear: curYearStr.substring(0,4-numYear.length()) + numYear;
                dateDO.setYear(Integer.valueOf(finalNumYear));
                dateDO.setYearMatchedStr(matchText);
                return text.replace(matchText,"[["+matchText+"]]");
            }
            matcher=yearPattern3.matcher(matchText);
            if (matcher.find()){
                String chsYear=matchText.substring(0,matchText.indexOf("年"));
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
     * j从字符串中解析出月份
     * @param text 要解析的字符串
     * @param date 解析结果
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
                String chsMonth=matchText.substring(0,matchText.indexOf("月"));

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
