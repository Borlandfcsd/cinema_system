package io.borlandfcsd.cinemasystem.controller;


import io.borlandfcsd.cinemasystem.entity.dto.ReserveTicketResponse;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.service.MovieService;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import io.borlandfcsd.cinemasystem.service.TicketService;
import io.borlandfcsd.cinemasystem.service.TimetableService;
import io.borlandfcsd.cinemasystem.service.impl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MovieSessionController {
    private MovieSessionService movieSessionService;
    private MovieService movieService;
    private TicketService ticketService;
    private TimetableService timetableService;


    @RequestMapping(value = "movieSessions", method = RequestMethod.GET)
    public String movieSessionList(Model model){
        model.addAttribute("movieSession", new MovieSession());
        model.addAttribute("timetable", timetableService.getTimetable());
        model.addAttribute("movieList", movieService.getAllMovies());

        return "movieSession/movieSessions";
    }

    @RequestMapping(value = "/movieSession/add", method = RequestMethod.POST)
    public String addSession(@ModelAttribute("movieSession")MovieSession movieSession,
                             RedirectAttributes redirect){
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
    public String removeSession(@PathVariable("id") int id, RedirectAttributes redirect){
        movieSessionService.removeMovieSession(id);
        redirect.addFlashAttribute("message"," has been removed");
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
        model.addAttribute("movieSession", movieSessionService.getMovieSession(id));
        model.addAttribute("tickets", ticketService.getTickets(id));
        model.addAttribute("reservedTicket", new Ticket());
        model.addAttribute("pathToPoster",movieService.getPathToPoster());

        return "movieSession/sessionPage";
    }

    @RequestMapping(value = "/sessionPage/reserveTickets",method = RequestMethod.POST)
    public @ResponseBody
    ReserveTicketResponse reserveTickets(@RequestBody TicketDto ticketDto, RedirectAttributes redirect) {
            int sessionID = ticketDto.getSessionID();
            MovieSession session = movieSessionService.getMovieSession(sessionID);
            ticketService.reserveTickets(ticketDto, session);
            ReserveTicketResponse resp = new ReserveTicketResponse();
            resp.setMessage("Tickets has been reserved success");
            return resp;
    }


    @Autowired
    public void setMovieSessionService(MovieSessionService movieSessionService) {
        this.movieSessionService = movieSessionService;
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Autowired
    public void setTicketService(TicketServiceImpl ticketServiceImpl) {
        this.ticketService = ticketServiceImpl;
    }
    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @Autowired
    public void setTimetableService(TimetableService timetableService) {
        this.timetableService = timetableService;
    }
}
