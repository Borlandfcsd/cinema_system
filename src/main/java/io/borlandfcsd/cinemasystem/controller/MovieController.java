package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.dto.MovieDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.service.MovieService;
import io.borlandfcsd.cinemasystem.validator.MovieValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class MovieController {
    private MovieService movieService;
    private MovieValidator movieValidator;

    @Autowired
    public MovieController(MovieService movieService, MovieValidator movieValidator) {
        this.movieService = movieService;
        this.movieValidator = movieValidator;
    }

    @RequestMapping(value = "admin/movies", method = RequestMethod.GET)
    public String getMoviesPage(Model model) {
        if(!model.containsAttribute("movieDto")) {
            model.addAttribute("movieDto", new MovieDto());
        }
        model.addAttribute("listMovies", movieService.getAllMovies());

        return "movie/movies";
    }


    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
    public String addMovie(@Valid @ModelAttribute("movieDto") final MovieDto movieDto,
                           RedirectAttributes redirect, final BindingResult bindingResult) {
        Movie movie = movieDto.getMovie();
        movieValidator.validate(movieDto, bindingResult);

        if (bindingResult.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.movieDto", bindingResult);
            redirect.addFlashAttribute("movieDto", movieDto);
            redirect.addFlashAttribute("exception", "didn't create.Fill in the required fields, please");
            return "redirect:/admin/movies";
        }

        String path = movieService.savePoster(movieDto.getPoster());
        movie.setPoster(path);

        if (movie.getId() == 0) {
            movieService.addMovie(movie);
            redirect.addFlashAttribute("message", "has been added");
        } else {
            movieService.updateMovie(movie);
            redirect.addFlashAttribute("message", "has been updated");
        }
        return "redirect:/admin/movies";
    }


    @RequestMapping(value = "/admin/removeMovie/{id}")
    public String removeMovie(@PathVariable("id") int id, RedirectAttributes redirect) {
        movieService.removeMovie(id);
        redirect.addFlashAttribute("message", "has been removed");
        //redirect.addFlashAttribute("exception", "This movie has active session");
        return "redirect:/admin/movies";
    }

    @RequestMapping(value = "/admin/editMovie/{id}")
    public String editMovie(@PathVariable("id") int id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("listMovies", movieService.getAllMovies());

        return "movie/movies";
    }

    @RequestMapping(value = "moviePage/{id}")
    public String moviePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("pathToPoster", movieService.getPathToPoster());
        model.addAttribute("movie", movieService.getMovieById(id));

        return "movie/moviePage";
    }


}
