package io.borlandfcsd.cinemasystem.repository;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Integer> {
}
