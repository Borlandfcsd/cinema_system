package io.borlandfcsd.cinemasystem.entity.dto.timetable;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Week {
    private final static int DAY_OF_WEEK = 7;
    private List<MovieSession> sessionsForWeek;
    private Map<String, Day> days;


    public Week(List<MovieSession> sessionsForWeek) {
        this.sessionsForWeek = sessionsForWeek;
        this.days = new LinkedHashMap<>();
        install();
    }

    private void install(){
        LocalDate now = LocalDate.now();
        addDay(now, new Day(now,getSessionsForDate(now)));
        for (int i = 1; i <= DAY_OF_WEEK; i++) {
            LocalDate currentDate = now.plusDays(i);
            addDay(currentDate, new Day(currentDate,getSessionsForDate(currentDate)));
        }
    }

    protected List<MovieSession> getSessionsForDate(LocalDate date) {
        return  sessionsForWeek.stream()
                .filter(s -> s.getBeginDate().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    protected Day getDayWithDate(LocalDate date) {
        for(Map.Entry<String,Day> day:days.entrySet()){
            Day current = day.getValue();
            if(current.getDate().isEqual(date)){
                return current;
            }
        }
        return null;
    }

    private void addDay(LocalDate date, Day day) {
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
