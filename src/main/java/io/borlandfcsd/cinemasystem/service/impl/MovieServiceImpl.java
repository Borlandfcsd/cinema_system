package io.borlandfcsd.cinemasystem.service.impl;

import io.borlandfcsd.cinemasystem.dao.GenericDao;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class MovieServiceImpl implements MovieService {
    private static String UPLOADED_FOLDER = "C://server//file_storage//images//";
    private final String PATH_TO_POSTER;
    private static  MovieService instance;
    private GenericDao movieDao;

    private MovieServiceImpl() {
        PATH_TO_POSTER = "/images/";
    }

    @SuppressWarnings("unchecked")
    public void addMovie(Movie movie) {
        movieDao.addEntity(movie);
    }
    @SuppressWarnings("unchecked")
    public Movie getMovieById(int id) {
        return (Movie) movieDao.getEntity(id);
    }
    @Override
    public Movie getMovieByTitle(String title) {
        List movies = movieDao.getEntitiesByColumnName("title",title);
        return (Movie)movies.get(0);
    }
    @SuppressWarnings("unchecked")
    public List<Movie> getAllMovies() {
        return  movieDao.getListEntities();
    }
    @SuppressWarnings("unchecked")
    public void updateMovie(Movie movie) {
        movieDao.updateEntity(movie);
    }

    @SuppressWarnings("unchecked")
    public void removeMovie(int id) {
        movieDao.removeEntity(id);
    }


    public String savePoster(MultipartFile poster) {
        try {
            byte[] bytes = poster.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + poster.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return poster.getOriginalFilename();
    }

    @Autowired
    private void setMovieDao(GenericDao movieDao) {
       this.movieDao = movieDao;
    }

    public String getPathToPoster() {
        return PATH_TO_POSTER;
    }

    public static MovieService getInstance() {
        if(instance == null) {
            instance = new MovieServiceImpl();
        }
        return instance;
    }
}
