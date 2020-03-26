package com.jackluan.bigflag.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        LocalDateTime yesterday = date.toInstant().atZone(zoneId).toLocalDateTime().minusSeconds(1);
        return Date.from(yesterday.atZone(zoneId).toInstant());
    }

    public static Date getTodayStart(){
        return getDayStart(new Date(), 0);
    }

    public static Date getTodayEnd(){
        return getYesterdayEnd(getDayStart(new Date(), 1));
    }

}
