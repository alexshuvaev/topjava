package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetween(T l, T start, T end) {
        if (l instanceof LocalTime) {
            return ((LocalTime) l).compareTo((LocalTime) start) >= 0 && ((LocalTime) l).compareTo((LocalTime) end) <= 0;
        } else if (l instanceof LocalDate) {
            return ((LocalDate) l).compareTo((LocalDate) start) >= 0 && ((LocalDate) l).compareTo((LocalDate) end) <= 0;
        }
        return false;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

