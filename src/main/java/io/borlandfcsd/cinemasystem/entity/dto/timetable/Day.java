package io.borlandfcsd.cinemasystem.entity.dto.timetable;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Day {
    private LocalDate date;
    private MovieSession lastSession;
    private List<MovieSession> sessionsForDay; // just list with all session for day
    private Timeline timeline; // sessions with free time space;
    private Map<Movie, List<MovieSession>> movieSessionsForMovie;

    public Day(LocalDate date, List<MovieSession> sessionsForDay){
        this.sessionsForDay = sessionsForDay;
        this.timeline = new Timeline(sessionsForDay);
        this.movieSessionsForMovie = new HashMap<>();
        this.date = date;
        install();
    }

    private void install(){
        for (MovieSession session : sessionsForDay) {
            if (session != null) {
                addSession(session);
            }
        }
    }

    private void addSession(MovieSession session) {
        checkLastSession(session);
        if (movieSessionsForMovie == null) {
            movieSessionsForMovie = new HashMap<>();
        }
        Movie key = session.getMovie();
        if (!movieSessionsForMovie.containsKey(key)) {
            movieSessionsForMovie.put(key, new ArrayList<>());
            movieSessionsForMovie.get(key).add(session);
        } else {
            movieSessionsForMovie.get(key).add(session);
        }
        timeline.install(sessionsForDay);
    }

    private void checkLastSession(MovieSession session) {
        if (lastSession == null) {
            lastSession = session;
        }
        LocalDateTime currentDate = lastSession.getBeginDate();
        LocalDateTime pretender = session.getBeginDate();
        if (currentDate.isBefore(pretender)) {
            lastSession = session;
        }
    }
}
