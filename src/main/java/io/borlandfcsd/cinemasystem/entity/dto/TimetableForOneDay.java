package io.borlandfcsd.cinemasystem.entity.dto;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TimetableForOneDay {
    private LocalDate date;
    private MovieSession lastSession;
    private List<MovieSession> allSession; // just list with all session for day
    private Timeline timeline; // sessions with free time space;
    private Map<Movie, List<MovieSession>> movieSessionsMap; // group session for movie

    public void addSession(MovieSession session) {
        checkLastSession(session);
        if (movieSessionsMap == null) {
            movieSessionsMap = new HashMap<>();
        }
        Movie key = session.getMovie();
        if (!movieSessionsMap.containsKey(key)) {
            movieSessionsMap.put(key, new ArrayList<>());
            movieSessionsMap.get(key).add(session);
        } else {
            movieSessionsMap.get(key).add(session);
        }
        setTimeline(allSession);
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

    public Map<Movie, List<MovieSession>> getMovieSessionsMap() {
        if (movieSessionsMap == null) {
            return new HashMap<>();
        }
        return movieSessionsMap;
    }


    public void setTimeline(List<MovieSession> sessionList) {
        this.timeline = new Timeline(sessionList);
    }


}
