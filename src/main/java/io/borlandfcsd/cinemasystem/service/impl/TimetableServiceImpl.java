package io.borlandfcsd.cinemasystem.service.impl;

import io.borlandfcsd.cinemasystem.entity.dto.Timetable;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import io.borlandfcsd.cinemasystem.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimetableServiceImpl implements TimetableService {
    private static TimetableServiceImpl instance;
    private MovieSessionService movieSessionService;

    private TimetableServiceImpl() {

    }

    @Override
    public Timetable getTimetable() {
        List<MovieSession> sessions = movieSessionService.getMovieSessions();
        return new Timetable(sessions);
    }

    @Autowired
    private void setMovieSessionService(MovieSessionService movieSessionService) {
        this.movieSessionService = movieSessionService;
    }

    public static TimetableServiceImpl getInstance() {
        if (instance == null) {
            instance = new TimetableServiceImpl();
        }
        return instance;
    }
}
