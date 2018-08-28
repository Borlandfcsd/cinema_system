package io.borlandfcsd.cinemasystem.entity.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class TimetableForWeek {
    private Map<String, TimetableForOneDay> days;

    public Map<String, TimetableForOneDay> getDays() {
        if (days == null) {
            return new LinkedHashMap<>();
        }
        return days;
    }

    public void setDays(Map<String, TimetableForOneDay> days) {
        this.days = days;
    }

    public void addDay(LocalDate date, TimetableForOneDay day) {
        if (days == null) {
            days = new LinkedHashMap<>();
        }
        days.put(setKeyName(date), day);
    }

    private String setKeyName(LocalDate date) {
        String dayOfWeek = formatDayName(date);
        return dayOfWeek + getTimestamp(date);
    }

    private String formatDayName(LocalDate date) {
        String dayName = date.getDayOfWeek().name();
        return dayName.substring(0, 1).toUpperCase() + dayName.substring(1).toLowerCase();
    }

    private String getTimestamp(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        return " (" + date.format(formatter) + ")";
    }

}
