package io.borlandfcsd.cinemasystem.repository;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Integer> {
    Integer removeById(int id);
    MovieSession findById(int id);
    List<MovieSession> findAllByBeginDateBetween(LocalDateTime date1, LocalDateTime date2);
}
