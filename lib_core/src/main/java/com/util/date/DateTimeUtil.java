/*
 * Copyright (C) 2017 贵阳货车帮科技有限公司
 */

package com.util.date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.util.thirdparty.Preconditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * author : taowang
 * date :2018/7/27
 * description:提供日期时间获取、格式化、转换、比较、范围计算的接口。
 **/

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class DateTimeUtil {
    public static final long ONE_MILLIS = 1;
    public static final long ONE_SECOND = 1000 * ONE_MILLIS;
    public static final long ONE_MINUTE = 60 * ONE_SECOND;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;
    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String FULL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";
    public static final String FULL_TIME_FORMAT_PATTERN = "HH:mm:ss:SSS";

    private DateTimeUtil() {
        throw new AssertionError("Don't instance!");
    }

    /**
     * 得到系统当前日期及时间，并以{@link DateTimeUtil#DEFAULT_DATE_FORMAT_PATTERN}的格式返回。
     *
     * @return 默认样式的日期时间字符串。
     */
    public static String getCurrentTimeFormatted() {
        return format(System.currentTimeMillis(), DEFAULT_DATE_FORMAT_PATTERN);
    }

    /**
     * 用给定的模式将当前系统时间格式化成日期时间字符串。
     *
     * @param pattern 描述日期和时间的格式。比如"yyyy-MM-dd"返回日期为"2017-08-07"。
     * @return 已格式化的时间字符串。
     * @throws NullPointerException     when the pattern is null.
     * @throws IllegalArgumentException if the given pattern is invalid
     */
    public static String getCurrentTimeFormatted(@NonNull String pattern) {
        return format(System.currentTimeMillis(), pattern);
    }

    /**
     * 将一个 Date 对象转换成给定模式的日期时间字符串。
     *
     * @param date    要格式化为时间字符串的时间值。
     * @param pattern 描述日期和时间的模式。比如"yyyy-MM-dd"返回日期为"2017-08-07"。
     * @return 已格式化的时间字符串。
     * @throws NullPointerException     when the date or pattern is null
     * @throws IllegalArgumentException if the given pattern is invalid
     */
    public static String format(@NonNull Date date, @NonNull String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * 用给定的模式将毫秒转化成日期时时间字符串。
     *
     * @param millis  要格式化为时间字符串的毫秒值
     * @param pattern 描述日期和时间的模式。比如"yyyy-MM-dd"返回日期为"2017-08-07"
     * @return 已格式化的时间字符串。
     * @throws NullPointerException     when the pattern is null
     * @throws IllegalArgumentException if the given pattern is invalid
     */
    public static String format(long millis, @NonNull String pattern) {
        return format(new Date(millis), pattern);
    }

    /**
     * 将给定模式的日期时间字符串转换成日期 Date 对象。
     *
     * @param srcDate 日期时间字符串。
     * @param pattern 描述日期时间字符串的模式。比如"yyyy-MM-dd"返回日期为"2017-08-07"。
     * @return Date对象。
     * @throws ParseException           if the beginning of the specified string cannot be parsed.
     * @throws IllegalArgumentException if the given pattern is invalid
     * @throws NullPointerException     if the given pattern is null
     */
    public static Date convert2Date(@NonNull String srcDate, @NonNull String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.parse(srcDate);
    }

    /**
     * 将给定模式的日期时间字符串转换成毫秒值。
     *
     * @param srcDate 日期时间字符串。
     * @param pattern 描述日期时间字符串的模式。比如"yyyy-MM-dd"返回日期为"2017-08-07"
     * @return 毫秒值。
     * @throws ParseException           if the specified string cannot be parsed.
     * @throws IllegalArgumentException if the given pattern is invalid.
     * @throws NullPointerException     if srcDate or pattern is null.
     */
    public static long convert2Millis(@NonNull String srcDate, @NonNull String pattern) throws ParseException {
        Date date = convert2Date(srcDate, pattern);
        return date.getTime();
    }

    /**
     * 将当前模式的日期时间字符串转换成给定模式的日期时间字符串。
     *
     * @param srcDateStr    时间字符串。
     * @param srcPattern    描述当前日期时间模式。
     * @param targetPattern 描述日期时间字符串的目标模式。
     * @return 已转换的日期时间字符串。
     * @throws ParseException           if the beginning of the specified string with srcDateStr cannot be parsed.
     * @throws IllegalArgumentException if the given srcPattern targetPattern is invalid
     * @throws NullPointerException     if srcDateStr or srcPattern or targetPattern is null.
     */
    @Nullable
    public static String convert(@NonNull String srcDateStr, @NonNull String srcPattern,
                                 @NonNull String targetPattern) throws ParseException {
        Date date = convert2Date(srcDateStr, srcPattern);
        return format(date, targetPattern);
    }

    /**
     * 秒转换成毫秒。
     *
     * @param second 秒。
     * @return 毫秒。
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long second2Millis(long second) {
        Preconditions.checkArgument(second >= 0,
                "second must be greater than than or equal to 0.");
        return second * ONE_SECOND;
    }

    /**
     * 分钟转换成毫秒。
     *
     * @param minute 分。
     * @return 毫秒。
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long minute2Millis(int minute) {
        Preconditions.checkArgument(minute >= 0,
                "minute must be greater than than or equal to 0.");
        return minute * ONE_MINUTE;
    }

    /**
     * 小时转换成毫秒。
     *
     * @param hour 时。
     * @return 毫秒。
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long hour2Millis(int hour) {
        Preconditions.checkArgument(hour >= 0,
                "hour must be greater than than or equal to 0.");
        return hour * ONE_HOUR;
    }

    /**
     * 日转换成毫秒。
     *
     * @param day 日期数。
     * @return 毫秒。
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long day2Millis(int day) {
        Preconditions.checkArgument(day >= 0,
                "day must be greater than or equal to 0.");
        return day * ONE_DAY;
    }

    /**
     * 毫秒转成秒。
     * <p>
     * 1、如果给定的时间在两个秒周期之间时，转换过程中会丢失精度。
     * <br/>
     * 例如：1200ms返回 1
     * <br/>
     * 2、如果给定的毫秒值不满1秒，返回0
     * <br/>
     * 例如：999ms返回 0
     *
     * @param millis 毫秒值。
     * @return 秒数。
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long millis2Second(long millis) {
        Preconditions.checkArgument(millis > 0, "millis must be greater than 0.");
        return millis / ONE_SECOND;
    }

    /**
     * 毫秒转换为分
     * <p>
     * 1、如果给定的时间在两个分钟周期之间时，转换过程中会丢失精度。
     * <br/>
     * 2、如果给定的毫秒值不满1秒分钟，返回 0
     *
     * @param millis 毫秒。
     * @return 分。
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long millis2Minute(long millis) {
        Preconditions.checkArgument(millis > 0, "millis must be greater than 0.");
        return millis / ONE_MINUTE;
    }

    /**
     * 毫秒转换为小时
     * * <p>
     * 1、如果给定的时间在两个小时周期之间时，转换过程中会丢失精度。
     * <br/>
     * 2、如果给定的毫秒值不满1小时，返回 0
     *
     * @param millis 毫秒
     * @return 小时
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long millis2Hour(long millis) {
        Preconditions.checkArgument(millis > 0, "millis must be greater than 0.");
        return millis / ONE_HOUR;
    }

    /**
     * 毫秒转换为日
     * <p>
     * 1、如果给定的时间在两个日期周期之间时，转换过程中会丢失精度。
     * <br/>
     * 2、如果给定的毫秒值不满1天，返回 0
     *
     * @param millis 毫秒
     * @return 天
     * @throws IllegalArgumentException if the time interval less than 0.
     */
    public static long millis2Day(long millis) {
        Preconditions.checkArgument(millis > 0, "millis must be greater than 0.");
        return millis / ONE_DAY;
    }

    /**
     * 比较两个时间
     * <br/>
     * 参与比较的时间字符串的格式必须是一样
     *
     * @param srcDate   日期字符串
     * @param otherDate 比较的日期字符串
     * @param pattern   描述日期和时间的格式。
     * @return srcDate < otherDate 结果小于0;
     * <br/>
     * srcDate == otherDate 结果等于0;
     * <br/>
     * srcDate > otherDate 结果大于0;
     * @throws ParseException           if the beginning of the specified string cannot be parsed.
     * @throws IllegalArgumentException if the given pattern is invalid
     * @throws NullPointerException     if the srcDate or otherDate or pattern is null
     */
    public static int compare(@NonNull String srcDate, @NonNull String otherDate,
                              @NonNull String pattern) throws ParseException {
        long srcDateMillis = convert2Millis(srcDate, pattern);
        long otherDateMillis = convert2Millis(otherDate, pattern);
        return compare(srcDateMillis, otherDateMillis);
    }

    /**
     * 比较两个时间
     *
     * @param srcDateMillis   时间毫秒
     * @param otherDateMillis 时间毫秒
     * @return srcDateMillis < otherDateMillis 结果小于0;
     * <br/>
     * srcDateMillis == otherDateMillis 结果等于0;
     * <br/>
     * srcDateMillis > otherDateMillis 结果大于0;
     */
    public static int compare(long srcDateMillis, long otherDateMillis) {
        return new Date(srcDateMillis).compareTo(new Date(otherDateMillis));
    }

    /**
     * 判断给定的时间值，是否在指定的区间内。当 给定时间=区间最小时间=区间最大时间 时判定为在当前区间内
     *
     * @param targetMillis 目标时间的时间戳
     * @param minMillis    区间时间开始的时间戳
     * @param maxMillis    区间时间结束的时间戳
     * @return 如果目标时间在给定区间时间内返回true.否则返回false
     */
    public static boolean rangeOf(long targetMillis, long minMillis, long maxMillis) {
        return (minMillis <= targetMillis && maxMillis >= targetMillis);
    }

    /**
     * 判断给定的时间值，是否在指定的区间内。当 给定时间=区间最小时间=区间最大时间 时判定为在当前区间内。
     * <br/>
     * 给定的目标时间、区间最小时间、区间最大时间的格式必须是一样的，否则会产生{@link ParseException}异常
     *
     * @param targetDate 目标时间
     * @param minDate    区间最小时间
     * @param maxDate    区间最大时间值
     * @param pattern    描述日期和时间的格式。比如"yyyy-MM-dd"返回日期为"2017-08-07"
     * @return 如果目标时间在给定区间时间内返回true.否则返回false
     * @throws ParseException           if the beginning of the specified string cannot be parsed.
     * @throws IllegalArgumentException if the given pattern is invalid.
     * @throws NullPointerException     if the targetDate or minDate or maxDate or pattern is null.
     */
    public static boolean rangeOf(@NonNull String targetDate, @NonNull String minDate,
                                  @NonNull String maxDate, @NonNull String pattern) throws ParseException {
        long targetMillis = convert2Millis(targetDate, pattern);
        long minMillis = convert2Millis(minDate, pattern);
        long maxMillis = convert2Millis(maxDate, pattern);
        return rangeOf(targetMillis, minMillis, maxMillis);
    }

    /**
     * 获取当天第一时刻的毫秒值。即0点0分0秒0毫秒对应的时间戳
     *
     * @return 毫秒
     */
    public static long todayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当天最后时刻的毫秒值。即23点59分59秒999毫秒对应的时间戳
     *
     * @return 毫秒
     */
    public static long todayEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}
