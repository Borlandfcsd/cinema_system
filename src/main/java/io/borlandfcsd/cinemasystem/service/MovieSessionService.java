package io.borlandfcsd.cinemasystem.service;

import io.borlandfcsd.cinemasystem.entity.dto.timetable.Day;
import io.borlandfcsd.cinemasystem.entity.dto.timetable.Timetable;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionService {
    void addMovieSession(MovieSession movieSession);

    MovieSession getMovieSession(int id);

    List<MovieSession> getMovieSessions();

    Timetable getTimetable();

    Day getDayWithDate(LocalDate date);

    List<MovieSession> getMovieSessionsForDay(LocalDate date);

    void updateMovieSession(MovieSession movieSession);

    void removeMovieSession(int id);
}
