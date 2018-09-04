package io.borlandfcsd.cinemasystem.service.impl.jpa;

import io.borlandfcsd.cinemasystem.entity.comparator.MovieSessionComparator;
import io.borlandfcsd.cinemasystem.entity.dto.timetable.Timetable;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.repository.MovieSessionRepository;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service(value = "movieSessionService")
public class MovieSessionServiceImpl implements MovieSessionService {

    private MovieSessionRepository movieSessionRepository;
    private Comparator<MovieSession> comparator;

    @Autowired
    public MovieSessionServiceImpl(MovieSessionRepository movieSessionRepository) {
        this.movieSessionRepository = movieSessionRepository;
        this.comparator = new MovieSessionComparator();
    }

    @Transactional
    public void addMovieSession(MovieSession movieSession) {
        movieSessionRepository.save(movieSession);
    }

    @Transactional
    public MovieSession getMovieSession(int id) {
        return movieSessionRepository.findById(id);
    }

    @Transactional
    public List<MovieSession> getMovieSessions() {
        return movieSessionRepository.getAllByOrderByBeginDateAsc();
    }

    @Transactional
    public List<MovieSession> getMovieSessionsForDay(LocalDate date) {
        LocalDateTime beginDay = LocalDateTime.of(date,LocalTime.of(0,0,0));
        LocalDateTime endDay = LocalDateTime.of(date,LocalTime.of(23,59,59));
        return movieSessionRepository.findAllByBeginDateBetween(beginDay,endDay);
    }

    @Transactional
    public Timetable getTimetable(){
        LocalDateTime begin = LocalDate.now().atTime(0,0);
        LocalDateTime end = LocalDate.now().plusDays(6).atTime(23,59);
        List<MovieSession> list = movieSessionRepository.findAllByBeginDateBetween(begin,end);
        sort(list);
        return new Timetable(list);
    }

    @Transactional
    public void updateMovieSession(MovieSession movieSession) {
        movieSessionRepository.saveAndFlush(movieSession);
    }

    @Transactional
    public void removeMovieSession(int id) {
        movieSessionRepository.removeById(id);
    }

    private void sort(List<MovieSession> list){
        list.sort(comparator);
    }
}
