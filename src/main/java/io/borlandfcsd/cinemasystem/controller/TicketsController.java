package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.dto.ReserveTicketResponse;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import io.borlandfcsd.cinemasystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TicketsController {
    private MovieSessionService movieSessionService;
    private TicketService ticketService;

    @Autowired
    public TicketsController(MovieSessionService movieSessionService, TicketService ticketService) {
        this.movieSessionService = movieSessionService;
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/tickets/reserveTickets", method = RequestMethod.POST)
    public @ResponseBody
    ReserveTicketResponse reserveTickets(@RequestBody TicketDto ticketDto) {
        int sessionID = ticketDto.getSessionID();
        MovieSession session = movieSessionService.getMovieSession(sessionID);
        ticketService.reserveTickets(ticketDto, session);
        ReserveTicketResponse resp = new ReserveTicketResponse();
        resp.setMessage("Tickets has been reserved success");
        return resp;
    }
}
