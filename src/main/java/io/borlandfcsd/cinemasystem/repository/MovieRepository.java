package io.borlandfcsd.cinemasystem.repository;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
    List<Movie> getAllByOrderByTitleAsc();
    Integer removeById(int id);
    Movie findById(int id);
    Movie findByTitle(String title);
}
