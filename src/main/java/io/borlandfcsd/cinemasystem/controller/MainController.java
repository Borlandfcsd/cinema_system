package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.service.MovieService;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private MovieService movieService;
    private MovieSessionService movieSessionService;

    @Autowired
    public MainController(MovieService movieService, MovieSessionService movieSessionService) {
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
    }

    @RequestMapping(value = "/")
    public String showIndexPage(Model model) {
        model.addAttribute("pathToPoster", movieService.getPathToPoster());
        model.addAttribute("timetable", movieSessionService.getTimetable());
        model.addAttribute("message", null);
        return "index";
    }
}
