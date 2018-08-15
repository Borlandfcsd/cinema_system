package io.borlandfcsd.cinemasystem.entity.dto;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
    public static int BREAK_BETWEEN_SESSIONS = 25;
    private List<MovieSession> sessions;
    private TimetableForWeek week;


    public Timetable(List<MovieSession> sessions) {
        this.sessions = sortListByTime(sessions);
        this.week = setWeekTimetable();
    }

    private List<MovieSession> sortListByTime(List<MovieSession> sessions) {
        int size = sessions.size();
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < (size - i); j++) {
                LocalDateTime time1 = sessions.get(j - 1).getBeginDate();
                LocalDateTime time2 = sessions.get(j).getBeginDate();
                if (time1.isAfter(time2)) {
                    MovieSession temp = sessions.get(j);
                    sessions.set(j, sessions.get(j - 1));
                    sessions.set(j - 1, temp);
                }
            }
        }
        return sessions;
    }

    private TimetableForWeek setWeekTimetable() {
        this.week = new TimetableForWeek();
        LocalDate now = LocalDate.now();
        for (int i = 0; i <= 6; i++) {
            LocalDate currentDate = now.plusDays(i);
            week.addDay(currentDate, getTimetableForDay(currentDate));
        }
        return week;
    }

    private TimetableForOneDay getTimetableForDay(LocalDate date) {
        TimetableForOneDay day = new TimetableForOneDay();
        List<MovieSession> sessionList = getSessionsForDate(date);
        day.setDate(date);
        day.setAllSession(sessionList);
        day.setTimeline(sessionList);

        for (MovieSession session : sessionList) {
            if (session != null) {
                day.addSession(session);
            }
        }
        return day;
    }

    private List<MovieSession> getSessionsForDate(LocalDate date) {
        List<MovieSession> listSessions = new ArrayList<>();
        for (MovieSession session : sessions) {
            LocalDate sessionDate = session.getBeginDate().toLocalDate();
            if (date.isEqual(sessionDate)) {
                listSessions.add(session);
            }
        }
        return sortListByTime(listSessions);
    }

    public String getFreeTimeForNextSession() {
        TimetableForOneDay day = getTimetableForDay(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        MovieSession lastSession = day.getLastSession();
        LocalDateTime nextSession;
        if (lastSession != null) {
            nextSession = lastSession.getEndDate()
                    .plusMinutes(BREAK_BETWEEN_SESSIONS);
        } else {
            nextSession = LocalDateTime.now();
        }
        return formatter.format(nextSession);
    }

    public List<MovieSession> getSessions() {
        return sessions;
    }

    public TimetableForWeek getWeek() {
        return week;
    }
}
