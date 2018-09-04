package io.borlandfcsd.cinemasystem.entity.dto.timetable;

import io.borlandfcsd.cinemasystem.entity.comparator.MovieSessionComparator;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;


@Getter
public class Timetable {
    public static  final LocalTime CINEMA_OPENING_TIME = LocalTime.of(8,0);
    public static  final LocalTime CINEMA_CLOSING_TIME = LocalTime.of(22,0);
    public static final LocalTime MAX_SESSION_BEGIN_TIME = LocalTime.of(20, 0);
    public static int BREAK_BETWEEN_SESSIONS = 10;
    private Week week;


    public Timetable(List<MovieSession> sessions) {
        this.week = new Week(sortListByTime(sessions));
    }

    private List<MovieSession> sortListByTime(List<MovieSession> sessions) {
        Comparator<MovieSession> comparator = new MovieSessionComparator();
        sessions.sort(comparator);
        return sessions;
    }

    public List<MovieSession> getSessionsForWeek(){
       return week.getSessionsForWeek();
    }

    private List<MovieSession> getWeekSessionsForDate(LocalDate date){
        return week.getSessionsForDate(date);
    }

    public String getTimeForNextSession() {
        Timeline timeline = week.getDayWithDate(LocalDate.now()).getTimeline();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        List<MovieSession> freeTimeList= timeline.getFreeTimeList();
        if (!freeTimeList.isEmpty()) {
            return formatter.format(freeTimeList.get(0).getBeginDate());
        }
        return formatter.format(LocalDateTime.now());
    }

}
