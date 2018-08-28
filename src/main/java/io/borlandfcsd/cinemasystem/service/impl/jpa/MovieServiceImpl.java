package io.borlandfcsd.cinemasystem.service.impl.jpa;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.repository.MovieRepository;
import io.borlandfcsd.cinemasystem.service.MovieService;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service(value = "movieService")
public class MovieServiceImpl implements MovieService {
    private final static String UPLOADED_FOLDER = "poster.upload.path";
    private final static String PATH_TO_POSTER = "poster.get.path";

    private MovieRepository movieRepository;

    @Resource
    Environment env;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional
    public void addMovie(Movie movie) {
        movieRepository.saveAndFlush(movie);
    }

    @Transactional
    public Movie getMovieById(int id) {
       return movieRepository.findById(id);
    }

    @Transactional
    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Transactional
    public List<Movie> getAllMovies() {
        return  movieRepository.findAll();
    }

    @Transactional
    public void updateMovie(Movie movie) {
        movieRepository.saveAndFlush(movie);
    }

    @Transactional
    public void removeMovie(int id) {
        movieRepository.removeById(id);
    }

    @Override
    public String savePoster(MultipartFile poster) {
        try {
            byte[] bytes = poster.getBytes();
            Path path = Paths.get(env.getProperty(UPLOADED_FOLDER) + poster.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return poster.getOriginalFilename();
    }

    @Override
    public String getPathToPoster() {
        return env.getProperty(PATH_TO_POSTER);
    }
}
