package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.service.MovieService;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import io.borlandfcsd.cinemasystem.service.TicketService;
import io.borlandfcsd.cinemasystem.validator.MovieSessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovieSessionController {
    private MovieSessionService movieSessionService;
    private MovieService movieService;
    private TicketService ticketService;
    private MovieSessionValidator validator;

    @Autowired
    public MovieSessionController(MovieSessionService movieSessionService, MovieService movieService, TicketService ticketService, MovieSessionValidator validator) {
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.ticketService = ticketService;
        this.validator = validator;
    }

    @RequestMapping(value = "/admin/movieSessions", method = RequestMethod.GET)
    public String movieSessionList(Model model) {
        model.addAttribute("movieSession", new MovieSession());
        model.addAttribute("timetable", movieSessionService.getTimetable());
        model.addAttribute("movieList", movieService.getAllMovies());
        model.addAttribute("message", null);

        return "movieSession/movieSessions";
    }

    @RequestMapping(value = "/admin/movieSession/add", method = RequestMethod.POST)
    public String addSession(@ModelAttribute("movieSession") MovieSession movieSession,
                             BindingResult bindingResult,
                             RedirectAttributes redirect) {
        movieSession.setMovie(movieService.getMovieById(movieSession.getMovie().getId()));
        validator.validate(movieSession, bindingResult);
        if (bindingResult.hasErrors()) {
            return "movieSession/movieSessions";
        }

        if (movieSession.getId() == 0) {
            movieSessionService.addMovieSession(movieSession);
            redirect.addFlashAttribute("message", " has been added");
        } else {
            movieSessionService.updateMovieSession(movieSession);
            redirect.addFlashAttribute("message", " has been updated");
        }
        return "redirect:/admin/movieSessions";
    }

    @RequestMapping(value = "/admin/removeSession/{id}")
    public String removeSession(@PathVariable("id") int id, RedirectAttributes redirect) {
        movieSessionService.removeMovieSession(id);
        redirect.addFlashAttribute("message", " has been removed");
        return "redirect:/admin/movieSessions";
    }

    @RequestMapping(value = "/admin/editSession/{id}")
    public String editSession(@PathVariable("id") int id, Model model) {
        model.addAttribute("movieSession", movieSessionService.getMovieSession(id));
        model.addAttribute("movieSessionList", movieSessionService.getMovieSessions());
        model.addAttribute("movieList", movieService.getAllMovies());
        return "movieSession/movieSessions";
    }

    @RequestMapping(value = "/sessionPage/{id}")
    public String getSessionPage(@PathVariable("id") int id, Model model) {
        MovieSession session = movieSessionService.getMovieSession(id);
        model.addAttribute("movieSession", session);
        model.addAttribute("tickets", ticketService.getCinemaHallSchema(session));
        model.addAttribute("reservedTicket", new Ticket());
        model.addAttribute("pathToPoster", movieService.getPathToPoster());

        return "movieSession/sessionPage";
    }


}
