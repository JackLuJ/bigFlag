package com.jackluan.bigflag.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/18 0:29
 */
public class DateUtils {

    public static Date getDayStart(Date date, int dayCount) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate dayStart = date.toInstant().atZone(zoneId).toLocalDate().plusDays(dayCount);
        return Date.from(dayStart.atStartOfDay(zoneId).toInstant());
    }

    public static Date getYesterdayEnd(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime yesterday = LocalDateTime.of(date.toInstant().atZone(zoneId).toLocalDate(), LocalTime.MAX);
        return Date.from(yesterday.atZone(zoneId).toInstant());
    }

    public static Date getBeforeMinuteStart(Date date, int hourCount, int minuteCount) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime time = date.toInstant().atZone(zoneId).toLocalDateTime().minusHours(hourCount).minusMinutes(minuteCount).withSecond(0).withNano(0);
        return Date.from(time.atZone(zoneId).toInstant());
    }

    public static Date getBeforeMinuteEnd(Date date, int hourCount, int minuteCount) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime time = date.toInstant().atZone(zoneId).toLocalDateTime().minusHours(hourCount).minusMinutes(minuteCount).withSecond(59).withNano(999999999);
        return Date.from(time.atZone(zoneId).toInstant());
    }

    public static Date getTodayStart(){
        return getDayStart(new Date(), 0);
    }

    public static Date getTodayEnd(){
        return getYesterdayEnd(new Date());
    }

}
