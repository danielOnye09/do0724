package com.mortgage.tool_rental.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateUtils {
    public static boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static boolean isHoliday(LocalDate date) {
        return isIndependenceDay(date) || isLaborDay(date);
    }

    private static boolean isIndependenceDay(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4);
        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return date.equals(independenceDay.minusDays(1));
        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return date.equals(independenceDay.plusDays(1));
        } else {
            return date.equals(independenceDay);
        }
    }


    private static boolean isLaborDay(LocalDate date) {
        LocalDate firstMonday = LocalDate.of(date.getYear(), 9, 1).with(DayOfWeek.MONDAY);
        return date.equals(firstMonday);
    }

    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return date.format(formatter);
    }
}



