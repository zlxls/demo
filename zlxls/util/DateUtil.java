package com.zlxls.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * 日期操作类
 * @ClassNmae：DateUtil
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class DateUtil {
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Long转日期
     * @param l 时间毫秒
     * @return Date
     */
    public static Date LongToDate(long l){
        return new Date(l);
    }
    /**
     * 字符串毫秒转日期
     * @param l 时间毫秒
     * @return Date
     */
    public static Date LongToDate(String l){
        return new Date(Long.parseLong(l));
    }
        /**
     * 返回格式化日期
     * @param reg
     * @return Date
     */
    public static String DateToString(String reg){
        SimpleDateFormat time = new SimpleDateFormat(reg);
        return time.format(new Date());
    }
    /**
     * 格式化日期转换方法，年 月 日 时 分 秒
     * @param longTime 时间毫秒
     * @param yyyy_Type 年的格式化形式："年","/","-"等等
     * @param MM_type 月的格式化形式："月","/","-"等等
     * @param dd_type 日的格式化形式："日","/","-"等等
     * @param HH_type 时的格式化形式："时","/",";"等等
     * @param mm_type 分的格式化形式："分","/",":"等等
     * @param ss_type 秒的格式化形式："秒","/",":"等等
     * @return 格式化后的日期
     */
    public static String longToDateString(Long longTime,String yyyy_Type,String MM_type,String dd_type,String HH_type,String mm_type,String ss_type){
        String regString = "yyyy"+yyyy_Type+"MM"+MM_type+"dd"+dd_type+" HH"+HH_type+"mm"+mm_type+"ss"+ss_type;
        return longToDateString(longTime,regString);
    }
    /**
     * 格式化日期转换方法，年 月 日 时 分
     * @param longTime 时间毫秒
     * @param yyyy_Type 年的格式化形式："年","/","-"等等
     * @param MM_type 月的格式化形式："月","/","-"等等
     * @param dd_type 日的格式化形式："日","/","-"等等
     * @param HH_type 时的格式化形式："时","/",";"等等
     * @param mm_type 分的格式化形式："分","/",":"等等
     * @return 格式化后的日期
     */
    public static String longToDateString(Long longTime,String yyyy_Type,String MM_type,String dd_type,String HH_type,String mm_type){
        String regString = "yyyy"+yyyy_Type+"MM"+MM_type+"dd"+dd_type+" HH"+HH_type+"mm"+mm_type;
        return longToDateString(longTime,regString);
    }
    /**
     * 格式化日期转换方法，年 月 日 时
     * @param longTime 时间毫秒
     * @param yyyy_Type 年的格式化形式："年","/","-"等等
     * @param MM_type 月的格式化形式："月","/","-"等等
     * @param dd_type 日的格式化形式："日","/","-"等等
     * @param HH_type 时的格式化形式："时","/",";"等等
     * @return 格式化后的日期
     */
    public static String longToDateString(Long longTime,String yyyy_Type,String MM_type,String dd_type,String HH_type){
        String regString = "yyyy"+yyyy_Type+"MM"+MM_type+"dd"+dd_type+" HH"+HH_type;
        return longToDateString(longTime,regString);
    }
    /**
     * 格式化日期转换方法，年 月 日
     * @param longTime 时间毫秒
     * @param yyyy_Type 年的格式化形式："年","/","-"等等
     * @param MM_type 月的格式化形式："月","/","-"等等
     * @param dd_type 日的格式化形式："日","/","-"等等
     * @return 格式化后的日期
     */
    public static String longToDateString(Long longTime,String yyyy_Type,String MM_type,String dd_type){
        String regString = "yyyy"+yyyy_Type+"MM"+MM_type+"dd"+dd_type;
        return longToDateString(longTime,regString);
    }
    /**
     * 格式化日期转换方法，年 月
     * @param longTime 时间毫秒
     * @param yyyy_Type 年的格式化形式："年","/","-"等等
     * @param MM_type 月的格式化形式："月","/","-"等等
     * @return 格式化后的日期
     */
    public static String longToDateString(Long longTime,String yyyy_Type,String MM_type){
        String regString = "yyyy"+yyyy_Type+"MM"+MM_type;
        return longToDateString(longTime,regString);
    }
    /**
     * 格式化日期转换方法，自由格式化<br>
     * 1,传入整个格式化的参数模板<br>
     * 2,必须符合时间格式的参数模板
     * @param longTime 时间毫秒
     * @param regString yyyy/MM/dd HH:mm:ss yyyy年MM月dd日 HH时mm分秒ss 可格式化为年
     * @return 格式化后的日期
     */
    public static String longToDateString(Long longTime,String regString){
        SimpleDateFormat time = new SimpleDateFormat(regString);
        return time.format(longTime);
    }
    /**
     * 获取当天凌晨，夜间整点时间毫秒<br>
     * @param morning_1_enening_0 1早上 其他为夜间
     * @return long 毫秒
     * @throws java.text.ParseException
     */
    public static long getTodayToLong(int morning_1_enening_0) throws ParseException{
        if(morning_1_enening_0==1){
            return sdfTime.parse(sdfDay.format(new Date())+ " 00:00:00").getTime();
        }else{
            return sdfTime.parse(sdfDay.format(new Date())+ " 23:59:59").getTime();
        }
    }
    /**
     * 把格式化的日期补全
     * @param date
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String getStringFormatDate(String date){
        switch(date.length()){
            case 10:return date+" 00:00:00";//yyyy-MM-dd
            case 11:return date+"00:00:00";//yyyy-MM-dd 
            case 13:return date+":00:00";//yyyy-MM-dd HH
            case 16:return date+":00";//yyyy-MM-dd HH:mm
            case 21:return date;//yyyy-MM-dd HH:mm:ss
            default:return date;
        }
    }
    /**
     * 把格式化的日期转化为毫秒
     * @param date yyyy-MM-dd HH:mm:ss
     * @return long 毫秒
     * @throws ParseException 
     */
    public static long getTime(String date) throws ParseException{
        return sdfTime.parse(getStringFormatDate(date)).getTime();
    }
    /**
     * 获取YYYY格式
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }
    /**
     * 获取YYYY-MM-DD格式
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }
    /**
     * 获取YYYYMMDD格式
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }
    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }
    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }
    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static Date fomatDate(String date) {
        try {
            return sdfDay.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date fomatDate(String date, String format) {
        DateFormat fmt = new SimpleDateFormat(format);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 校验日期是否合法
     * @param s
     * @return
     */
    public static boolean isValidDate(String s) {
        try {
            sdfDay.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }
    public static int getDiffYear(String startTime, String endTime) {
        try {
            long aa = 0;
            int years = (int) (((sdfDay.parse(endTime).getTime() - sdfDay.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = sdfDay.parse(beginDateStr);
            endDate = sdfDay.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);
        return day;
    }
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        String dateStr = sdfTime.format(date);
        return dateStr;
    }
    /**
     * @return 
     * @Description: 获取四位月日
     * @date 2015年9月24日
     */
    public static String getFourDateNo() {
        Calendar canlendar = Calendar.getInstance(); // java.util包
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("MMdd");
        String dateStr = sdfd.format(date);
        return dateStr;
    }
    /**
     * @return 
     * @Description: 获取两位年
     * @date 2015年9月24日
     */
    public static String getTwoDateYear() {
        Calendar canlendar = Calendar.getInstance(); // java.util包
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yy");
        String dateStr = sdfd.format(date);
        return dateStr;
    }
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }
    /**
     * 获得本月的开始时间，即2012-01-01 00:00:00
     * @return
     */
    public static String getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        String now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = sdfDay.format(c.getTime()) + " 00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     * @return
     */
    public static String getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        String now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = sdfDay.format(c.getTime()) + " 23:59:59";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     * @return time
     */
    public static String getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                    c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                    c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                    c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                    c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = sdfDay.format(c.getTime()) + " 00:00:00";
        } catch (Exception e) {
                e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     * @return long
     * @throws java.text.ParseException
     */
    public static Long getCurrentQuarterStartTimeToLong() throws ParseException {
        return sdfTime.parse(getCurrentQuarterStartTime()).getTime();
    }
    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     * @return
     */
    public static String getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = sdfDay.format(c.getTime()) + " 23:59:59";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前季度的结束时间，即2012-01-1 00:00:00
     * @return long
     * @throws java.text.ParseException
     */
    public static Long getCurrentQuarterEndTimeToLong() throws ParseException {
        return sdfTime.parse(getCurrentQuarterEndTime()).getTime();
    }
    public static void main(String[] args) {
        System.out.println(getDays());
        System.out.println(getAfterDayWeek("3"));
        System.out.println(getFourDateNo());
        // TODO Auto-generated method stub  
        System.out.println("当天24点时间：" + getTimesnight().getTime());  
        System.out.println("当前时间：" + new Date().getTime());  
        System.out.println("当天0点时间：" + getTimesmorning().getTime());  
        System.out.println("昨天0点时间：" + getYesterdaymorning().getTime());  
        System.out.println("近7天时间：" + getWeekFromNow().getTime());  
        System.out.println("本周周一0点时间：" + getTimesWeekmorning().getTime());  
        System.out.println("本周周日24点时间：" + getTimesWeeknight().getTime());  
        System.out.println("本月初0点时间：" + getTimesMonthmorning().getTime());  
        System.out.println("本月未24点时间：" + getTimesMonthnight().getTime());  
        System.out.println("上月初0点时间：" + getLastMonthStartMorning().getTime());  
        System.out.println("本季度开始点时间：" + getCurrentQuarterStartTime());  
        System.out.println("本季度结束点时间：" + getCurrentQuarterEndTime());  
        System.out.println("本年开始点时间：" + getCurrentYearStartTime().getTime());  
        System.out.println("本年结束点时间：" + getCurrentYearEndTime().getTime());  
        System.out.println("上年开始点时间：" + getLastYearStartTime().getTime());  
    }
    // 获得当天0点时间  
    public static Date getTimesmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, 0);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime();  
  
  
    }  
    // 获得昨天0点时间  
    public static Date getYesterdaymorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTimeInMillis(getTimesmorning().getTime()-3600*24*1000);  
        return cal.getTime();  
    }  
    // 获得当天近7天时间  
    public static Date getWeekFromNow() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTimeInMillis( getTimesmorning().getTime()-3600*24*1000*7);  
        return cal.getTime();  
    }  
  
    // 获得当天24点时间  
    public static Date getTimesnight() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, 24);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime();  
    }  
  
    // 获得本周一0点时间  
    public static Date getTimesWeekmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return cal.getTime();  
    }  
  
    // 获得本周日24点时间  
    public static Date getTimesWeeknight() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getTimesWeekmorning());  
        cal.add(Calendar.DAY_OF_WEEK, 7);  
        return cal.getTime();  
    }  
  
    // 获得本月第一天0点时间  
    public static Date getTimesMonthmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return cal.getTime();  
    }  
  
    // 获得本月最后一天24点时间  
    public static Date getTimesMonthnight() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
        cal.set(Calendar.HOUR_OF_DAY, 24);  
        return cal.getTime();  
    }  
  
    public static Date getLastMonthStartMorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getTimesMonthmorning());  
        cal.add(Calendar.MONTH, -1);  
        return cal.getTime();  
    }  
    public static Date getCurrentYearStartTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));  
        return cal.getTime();  
    }  
  
    public static Date getCurrentYearEndTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getCurrentYearStartTime());  
        cal.add(Calendar.YEAR, 1);  
        return cal.getTime();  
    }  
  
    public static Date getLastYearStartTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getCurrentYearStartTime());  
        cal.add(Calendar.YEAR, -1);  
        return cal.getTime();  
    }  
}
