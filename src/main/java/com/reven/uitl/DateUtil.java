package com.reven.uitl;

import java.text.ParseException;
import java.text.ParsePosition;
/**
 * 使用ThreadLocal解决SimpleDateFormat线程不安全
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName:  DateUtil   
 * @Description: 日期工具类
 * @author huangruiwen
 * @date   2019年5月14日
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    private static final Object object = new Object();

    public static void main(String[] args) throws ParseException {

        logger.info("{}", DateUtil.getInteger(new Date(), Calendar.DAY_OF_MONTH));
        logger.info("{}", DateUtil.getInteger(new Date(), Calendar.DAY_OF_WEEK));
        logger.info("{}", DateUtil.getInteger(new Date(), Calendar.HOUR_OF_DAY));
        logger.info("{}", DateUtil.getInteger(new Date(), Calendar.YEAR));
        // 异常测试
        try {
            logger.info("{}", DateUtil.getInteger(null, Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("{}", DateUtil.addInteger(new Date(), Calendar.YEAR, 1));
        // 异常测试
        logger.info("addInteger={}", DateUtil.addInteger(null, Calendar.YEAR, 1));

        logger.info("addDay={}", DateUtil.addDay(new Date(), 1));
        logger.info("addDay={}", DateUtil.addDay(null, 1));

        logger.info("=========================");

        logger.info("isDate={}", DateUtil.isDate("2025-02-06"));
        logger.info("isDate={}", DateUtil.isDate("2025-02-30"));
        logger.info("isDate={}", DateUtil.isDate("2025-02-3"));

        // 算法？
        logger.info("getDateStyle={}", DateUtil.getDateStyle("2025-02-3"));
        logger.info("getDateStyle={}", DateUtil.getDateStyle("2025023"));
        logger.info("getDateStyle={}", DateUtil.getDateStyle("20250203"));
        logger.info("getDateStyle={}", DateUtil.getDateStyle("2025-02-03 21:04:08"));
        logger.info("getDateStyle={}", DateUtil.getDateStyle("2025-2-3"));
        logger.info("getDateStyle61={}", DateUtil.getDateStyle("25-02-2003"));

        logger.info("stringToDate={}", DateUtil.stringToDate("2025-2-3", DateStyle.YYYY_MM_DD));
        logger.info("stringToDate={}", DateUtil.stringToDate("20250203", DateStyle.YYYYMMDD));
        logger.info("stringToDate={}", DateUtil.stringToDate("2025-02-03 21:30:00", DateStyle.YYYY_MM_DD_HH_MM_SS));

        logger.info("stringToDate2={}", DateUtil.stringToDate("2025-2-3"));
        logger.info("stringToDate2={}", DateUtil.stringToDate("2025-02-03 21:30:00"));
        // 无对应日期格式
        logger.info("stringToDate2={}", DateUtil.stringToDate("18-03-2025 21:30:00"));

        logger.info("stringToDate3={}", DateUtil.stringToDate("18-03-2025", "dd-MM-yyyy"));
        logger.info("stringToDate3={}", DateUtil.stringToDate("18-03-2025 21:30:00", "dd-MM-yyyy HH:mm:ss"));

        logger.info("dateToString={}", DateUtil.dateToString(new Date(), "dd-MM-yyyy HH:mm:ss"));
        logger.info("dateToString={}", DateUtil.dateToString(new Date(), "dd-MM-yyyy"));
        logger.info("dateToString={}", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH"));

        logger.info("stringToString={}", DateUtil.stringToString("2025-03-18", "yyyy-MM-dd", "yyyyMMdd"));
        logger.info("stringToString={}",
                DateUtil.stringToString("2025-03-18", DateStyle.YYYY_MM_DD, DateStyle.DD_MM_YYYY));

        logger.info("addYear={}",
                DateUtil.dateToString(DateUtil.addYear(new Date(), -1), DateStyle.YYYY_MM_DD_HH_MM_SS));
        logger.info("addMonth={}",
                DateUtil.dateToString(DateUtil.addMonth(new Date(), -1), DateStyle.YYYY_MM_DD_HH_MM_SS));
        logger.info("addDay={}", DateUtil.dateToString(DateUtil.addDay(new Date(), -1), DateStyle.YYYY_MM_DD_HH_MM_SS));
        logger.info("addHour={}",
                DateUtil.dateToString(DateUtil.addHour(new Date(), -1), DateStyle.YYYY_MM_DD_HH_MM_SS));
        logger.info("addMinute={}",
                DateUtil.dateToString(DateUtil.addMinute(new Date(), -1), DateStyle.YYYY_MM_DD_HH_MM_SS));
        logger.info("addSecond={}",
                DateUtil.dateToString(DateUtil.addSecond(new Date(), -1), DateStyle.YYYY_MM_DD_HH_MM_SS));

        logger.info("getMonth={}", DateUtil.getMonth(new Date()));

        logger.info("{}", DateUtil.dateToString(new Date(), DateStyle.YYYYMMDDHHMMSSSSS));

        logger.info("{}", DateUtil.getAge(DateUtil.stringToDate("2000-01-01", DateStyle.YYYY_MM_DD)));

    }

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取日期中的某数值。如获取月份
     * @param date 日期
     * @param dateType 日期格式 常用格式Calendar.YEAR、Calendar.DAY_OF_MONTH、Calendar.HOUR_OF_DAY、Calendar.DAY_OF_WEEK
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        if (date == null) {
            throw new RuntimeException("parameter date is null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期
     * @param dateType 类型
     * @param amount 数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<>();
        List<Long> absoluteValues = new ArrayList<>();

        if (timestamps != null && !timestamps.isEmpty()) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
                // 因此不能将minAbsoluteValue取默认值0
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期
     * 
     * @param date 日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null && getDateStyle(date) != null) {
            isDate = true;
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格DateStyle。失敗返回null。
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<>();
        List<Long> timestamps = new ArrayList<>();
        for (DateStyle style : DateStyle.values()) {
            if (style.isShowOnly()) {
                continue;
            }
            Date dateTmp = null;
            if (date != null) {
                try {
                    ParsePosition pos = new ParsePosition(0);
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                    if (pos.getIndex() != date.length()) {
                        dateTmp = null;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期.
     * @param date  日期字符串
     * @param dateStyle 日期风格 常用DateStyle.YYYYMMDD,DateStyle.YYYY_MM_DD
     * @return 日期
     * @throws ParseException 
     */
    public static Date stringToDate(String date, DateStyle dateStyle) throws ParseException {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = stringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期.自动匹配DateStyle中列举的日期格式，如果无法匹配将返回null
     * @param date 日期字符串
     * @return 日期
     * @throws ParseException 
     */
    public static Date stringToDate(String date) throws ParseException {
        DateStyle dateStyle = getDateStyle(date);
        return stringToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。推荐使用StringToDate(String date, DateStyle dateStyle)
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return 日期
     * @throws ParseException 
     */
    public static Date stringToDate(String date, String pattern) throws ParseException {
        Date myDate = null;
        if (date != null) {
            myDate = getDateFormat(pattern).parse(date);
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String dateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            dateString = getDateFormat(pattern).format(date);
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。
     * @param date 日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String dateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = dateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串
     * @param date 旧日期字符串
     * @param olddPattern 旧日期格式
     * @param newPattern 新日期格式
     * @return 新日期字符串
     * @throws ParseException 
     */
    public static String stringToString(String date, String olddPattern, String newPattern) throws ParseException {
        return dateToString(stringToDate(date, olddPattern), newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。
     * @param date 旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     * @throws ParseException 
     */
    public static String stringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle)
            throws ParseException {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = stringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。 
     * 
     * @param date 日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。
     * @param date 日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的天数。
     * @param date 日期
     * @param dayAmount  增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。
     * 
     * @param date 日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。
     * 
     * @param date 日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的秒钟。
     * 
     * @param date 日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 获取日期的年份。
     * 
     * @param date 日期字符串
     * @return 年份
     * @throws ParseException 
     */
    public static int getYear(String date) throws ParseException {
        return getYear(stringToDate(date));
    }

    /**
     * 获取日期的年份。 
     * 
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。 
     * @param date 日期字符串
     * @return 月份
     * @throws ParseException 
     */
    public static int getMonth(String date) throws ParseException {
        return getMonth(stringToDate(date));
    }

    /**
     * 获取日期的月份。 
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数。 
     * @param date 日期字符串
     * @return 天
     * @throws ParseException 
     */
    public static int getDay(String date) throws ParseException {
        return getDay(stringToDate(date));
    }

    /**
     * 获取日期的天数。 
     * @param date 日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。 
     * @param date 日期字符串
     * @return 小时
     * @throws ParseException 
     */
    public static int getHour(String date) throws ParseException {
        return getHour(stringToDate(date));
    }

    /**
     * 获取日期的小时。 
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。 
     * @param date 日期字符串
     * @return 分钟
     * @throws ParseException 
     */
    public static int getMinute(String date) throws ParseException {
        return getMinute(stringToDate(date));
    }

    /**
     * 获取日期的分钟。 
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取两个日期相差的天数
     * @param date 日期字符串
     * @param otherDate  另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     * @throws ParseException 
     */
    public static int getIntervalDays(String date, String otherDate) throws ParseException {
        return getIntervalDays(stringToDate(date), stringToDate(otherDate));
    }

    /**
     * @param date 日期
     * @param otherDate 另一个日期
     * @return 相差天数。如果失败则返回-1
     * @throws ParseException 
     */
    public static int getIntervalDays(Date date, Date otherDate) throws ParseException {
        Date dateTmp = DateUtil.stringToDate(DateUtil.dateToString(date, DateStyle.YYYY_MM_DD), DateStyle.YYYY_MM_DD);
        Date otherDateTmp = DateUtil.stringToDate(DateUtil.dateToString(otherDate, DateStyle.YYYY_MM_DD),
                DateStyle.YYYY_MM_DD);
        if (dateTmp == null || otherDateTmp == null) {
            throw new RuntimeException("日期转换失败");
        }
        long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
        return (int) (time / (24 * 60 * 60 * 1000));
    }

    /**
     * 获取年龄
     * @param birthday
     * @return      
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 获取月第一天：
     * @param inputDate
     * @return
     */
    public static Date getMonthFristDay(Date inputDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(inputDate);
        ca.add(Calendar.MONTH, 0);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return ca.getTime();
    }

    /**
     * 获取月的最后一天
     * @param inputDate
     * @return
     */
    public static Date getMonthLastDay(Date inputDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(inputDate);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();

    }

    /**
     * 获取月的指定的一天
     * @param inputDate
     * @return
     */
    public static Date getMonthDay(Date inputDate, int day) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(inputDate);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        ca.set(Calendar.DAY_OF_MONTH, day);
        return ca.getTime();
    }
}
