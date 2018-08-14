package io.borlandfcsd.cinemasystem.service;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {
    void addMovie(Movie movie);
    Movie getMovieById(int id);
    Movie getMovieByTitle(String title);
    List<Movie> getAllMovies();
    void updateMovie(Movie movie);
    void removeMovie(int id);
    String savePoster(MultipartFile poster);
    String getPathToPoster();
}
