package io.borlandfcsd.cinemasystem.controller;


import io.borlandfcsd.cinemasystem.entity.dto.ReserveTicketResponse;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.service.MovieService;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import io.borlandfcsd.cinemasystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovieSessionController {
    private MovieSessionService movieSessionService;
    private MovieService movieService;
    private TicketService ticketService;

    @Autowired
    public MovieSessionController(MovieSessionService movieSessionService, MovieService movieService, TicketService ticketService) {
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "movieSessions", method = RequestMethod.GET)
    public String movieSessionList(Model model) {
                model.addAttribute("movieSession", new MovieSession());
        model.addAttribute("timetable", movieSessionService.getTimetable());
        model.addAttribute("movieList", movieService.getAllMovies());

        return "movieSession/movieSessions";
    }

    @RequestMapping(value = "/movieSession/add", method = RequestMethod.POST)
    public String addSession(@ModelAttribute("movieSession") MovieSession movieSession,
                             RedirectAttributes redirect) {
        if (movieSession.getId() == 0) {
            movieSessionService.addMovieSession(movieSession);
            redirect.addFlashAttribute("message", " has been added");
        } else {
            movieSessionService.updateMovieSession(movieSession);
            redirect.addFlashAttribute("message", " has been updated");
        }
        return "redirect:/movieSessions";
    }

    @RequestMapping(value = "/removeSession/{id}")
    public String removeSession(@PathVariable("id") int id, RedirectAttributes redirect) {
        movieSessionService.removeMovieSession(id);
        redirect.addFlashAttribute("message", " has been removed");
        return "redirect:/movieSessions";
    }

    @RequestMapping(value = "editSession/{id}")
    public String editSession(@PathVariable("id") int id, Model model) {
        model.addAttribute("movieSession", movieSessionService.getMovieSession(id));
        model.addAttribute("movieSessionList", movieSessionService.getMovieSessions());
        model.addAttribute("movieList", movieService.getAllMovies());
        return "movieSession/movieSessions";
    }

    @RequestMapping(value = "sessionPage/{id}")
    public String getSessionPage(@PathVariable("id") int id, Model model) {
        MovieSession session = movieSessionService.getMovieSession(id);
        model.addAttribute("movieSession", session);
        model.addAttribute("tickets", ticketService.getTicketsForSession(session));
        model.addAttribute("reservedTicket", new Ticket());
        model.addAttribute("pathToPoster", movieService.getPathToPoster());

        return "movieSession/sessionPage";
    }

    @RequestMapping(value = "/sessionPage/reserveTickets", method = RequestMethod.POST)
    public @ResponseBody
    ReserveTicketResponse reserveTickets(@RequestBody TicketDto ticketDto, RedirectAttributes redirect) {
        int sessionID = ticketDto.getSessionID();
        MovieSession session = movieSessionService.getMovieSession(sessionID);
        ticketService.reserveTickets(ticketDto, session);
        ReserveTicketResponse resp = new ReserveTicketResponse();
        resp.setMessage("Tickets has been reserved success");
        return resp;
    }
}
