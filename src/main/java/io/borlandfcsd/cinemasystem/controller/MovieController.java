package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovieController {
    private MovieService movieService;


    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;

    }

    @RequestMapping(value = "admin/movies", method = RequestMethod.GET)
    public String getMoviesPage(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("listMovies", movieService.getAllMovies());

        return "movie/movies";
    }

    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
    public String addMovie(@ModelAttribute("movie") Movie movie,
                           @RequestParam("picture") MultipartFile poster,
                           RedirectAttributes redirect) {

        String path = movieService.savePoster(poster);
        movie.setPoster(path);
        if (movie.getId() == 0) {
            movieService.addMovie(movie);
            redirect.addFlashAttribute("message", " has been added");
        } else {
            movieService.updateMovie(movie);
            redirect.addFlashAttribute("message", " has been updated");
        }
        return "redirect:/admin/movies";
    }


    @RequestMapping(value = "/admin/removeMovie/{id}")
    public String removeMovie(@PathVariable("id") int id, RedirectAttributes redirect) {
        movieService.removeMovie(id);
        redirect.addFlashAttribute("message", " has been removed");
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
