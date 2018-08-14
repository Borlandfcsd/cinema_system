package io.borlandfcsd.cinemasystem.entity.dto;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private int BREAK_BETWEEN_SESSIONS = 25;
    private List<MovieSession> sessions;
    private TimetableForWeek week;
    private List<MovieSession> todayList;

    public Timetable(List<MovieSession> sessions) {
       this.sessions = sortListByTime(sessions);
       this.week = setWeekTimetable();
       this.todayList = getSpaceBetweenSession(LocalDate.now());
    }

    private List<MovieSession> sortListByTime(List<MovieSession> sessions){
        int size = sessions.size();
        for(int i=0; i < size; i++){
            for(int j=1; j < (size-i); j++){
                LocalDateTime time1 = sessions.get(j-1).getBeginDate();
                LocalDateTime time2 = sessions.get(j).getBeginDate();
                if(time1.isAfter(time2)){
                    MovieSession temp = sessions.get(j);
                    sessions.set(j,sessions.get(j-1));
                    sessions.set(j-1,temp);
                }
            }
        }
        return sessions;
    }

    private TimetableForWeek setWeekTimetable() {
        this.week = new TimetableForWeek();
        LocalDate now = LocalDate.now();
        for (int i = 0; i <= 6 ; i++) {
            LocalDate currentDate = now.plusDays(i);
            week.addDay(currentDate,getTimetableForDay(currentDate));
        }
        return week;
    }

    private TimetableForOneDay getTimetableForDay(LocalDate date) {
        TimetableForOneDay day = new TimetableForOneDay();
        List<MovieSession> listSession = getSessionsForDate(date);

        day.setDate(date);
        day.setSessionsForDay(listSession);

        for (MovieSession session : listSession) {
            if(session != null) {
                day.addSession(session);
            }
        }
        return day;
    }

    private List<MovieSession> getSessionsForDate(LocalDate date){
        List<MovieSession> listSessions = new ArrayList<>();
        for(MovieSession session : sessions){
            LocalDate sessionDate = session.getBeginDate().toLocalDate();
            if(date.isEqual(sessionDate)){
                listSessions.add(session);
            }
        }
        return sortListByTime(listSessions);
    }

    private List<MovieSession> getSpaceBetweenSession(LocalDate date){
        List<MovieSession> listSessions = getSessionsForDate(date);
        for (int i = 0; i < listSessions.size(); i++) {
            Movie movie = getRangeBetweenSession(listSessions, i);
            if(movie != null){
                MovieSession freeTime = new MovieSession();
                freeTime.setMovie(movie);
                freeTime.setBeginDate(listSessions.get(i).getEndDate());
                listSessions.add(i+1,freeTime);
                i++;
            }
        }
        return listSessions;
    }

    private Movie getRangeBetweenSession(List<MovieSession> listSessions, int i) {
        LocalDateTime session1 = listSessions.get(i).getEndDate();
        LocalDateTime session2;
        if(i+1 < listSessions.size()){
            session2 = listSessions.get(i+1).getBeginDate();
        } else {
            LocalTime time = LocalTime.of(20,0);
            session2 = LocalDate.now().atTime(time);
        }
        long range = ChronoUnit.MINUTES.between(session1,session2);
        if(range >= 120){
            Movie freeTime = new Movie();
            freeTime.setTitle("free time");
            freeTime.setDuration((int)range);
            return freeTime;
        }
        return null;
    }

    public String getFreeTimeForNextSession(){
        TimetableForOneDay day = getTimetableForDay(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        MovieSession lastSession = day.getLastSession();
        LocalDateTime nextSession;
        if(lastSession != null){
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

    public List<MovieSession> getTodayList() {
        return todayList;
    }
}
