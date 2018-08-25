package io.borlandfcsd.cinemasystem.repository;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
