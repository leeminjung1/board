package com.leeminjung1.infrastructure.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static String getDiffTime(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (ChronoUnit.HOURS.between(localDateTime, now) <= 24) {
            if (ChronoUnit.MINUTES.between(localDateTime, now) <= 60) {
                if (ChronoUnit.SECONDS.between(localDateTime, now) <= 60) {
                    return (ChronoUnit.SECONDS.between(localDateTime, now) + "초 전");
                }
                return (ChronoUnit.MINUTES.between(localDateTime, now) + "분 전");
            }
            return "약 " + (ChronoUnit.HOURS.between(localDateTime, now) + "시간 전");
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
    }

    public static String getDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
    }
}
