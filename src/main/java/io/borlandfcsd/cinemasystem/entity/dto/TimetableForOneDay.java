package io.borlandfcsd.cinemasystem.entity.dto;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimetableForOneDay {
    private LocalDate date;
    private MovieSession lastSession;
    private List<MovieSession> sessionsForDay;
    private Map<Movie,List<MovieSession>> movieSessionsMap;

    public void addSession(MovieSession session){
        checkLastSession(session);
        if (movieSessionsMap == null){
            movieSessionsMap = new HashMap<>();
        }
        Movie key = session.getMovie();
        if (!movieSessionsMap.containsKey(key)) {
            movieSessionsMap.put(key, new ArrayList<>());
            movieSessionsMap.get(key).add(session);
        } else {
            movieSessionsMap.get(key).add(session);
        }
    }

    private void checkLastSession(MovieSession session){
        if(lastSession == null){
            lastSession = session;
        }
        LocalDateTime currentDate = lastSession.getBeginDate();
        LocalDateTime pretender = session.getBeginDate();
        if (currentDate.isBefore(pretender)){
            lastSession = session;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<Movie, List<MovieSession>> getMovieSessionsMap() {
        if (movieSessionsMap == null){
            return new HashMap<>();
        }
        return movieSessionsMap;
    }

    public void setMovieSessionsMap(Map<Movie, List<MovieSession>> day) {
        this.movieSessionsMap = day;
    }

    public MovieSession getLastSession() {
        return lastSession;
    }

    public List<MovieSession> getSessionsForDay() {
        return sessionsForDay;
    }

    public void setSessionsForDay(List<MovieSession> sessionsForDay) {
        this.sessionsForDay = sessionsForDay;
    }

}
