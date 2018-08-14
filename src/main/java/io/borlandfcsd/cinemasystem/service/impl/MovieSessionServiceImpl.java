package io.borlandfcsd.cinemasystem.service.impl;

import io.borlandfcsd.cinemasystem.dao.GenericDao;
import io.borlandfcsd.cinemasystem.entity.dto.TimetableForOneDay;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class MovieSessionServiceImpl implements MovieSessionService {
    private static MovieSessionServiceImpl instance;
    private GenericDao movieSessionDao;

    private MovieSessionServiceImpl() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void addMovieSession(MovieSession movieSession) {
        movieSessionDao.addEntity(movieSession);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MovieSession getMovieSession(int id) {
        return (MovieSession) movieSessionDao.getEntity(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MovieSession> getMovieSessions() {
        return movieSessionDao.getListEntities();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MovieSession> getMovieSessionsForDay(LocalDate date) {
        List<MovieSession> movieSessions = movieSessionDao.getListEntities();
        List<MovieSession> result = new ArrayList<>();
        for (int i = 0; i < movieSessions.size(); i++) {
            LocalDate sessionDate = movieSessions.get(i).getBeginDate().toLocalDate();
            if(date.isEqual(sessionDate)) {
                result.add(movieSessions.get(i));
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateMovieSession(MovieSession movieSession) {
        movieSessionDao.updateEntity(movieSession);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeMovieSession(int id) {
        movieSessionDao.removeEntity(id);
    }


    @Autowired
    public void setMovieSessionDao(GenericDao movieSessionDao) {
        this.movieSessionDao = movieSessionDao;
    }

    public static MovieSessionService getInstance() {
        if(instance == null) {
            instance = new MovieSessionServiceImpl();
        }
        return instance;
    }
}
