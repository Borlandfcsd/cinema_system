package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.dto.AjaxResponse;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import io.borlandfcsd.cinemasystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class TicketsController {
    private MovieSessionService movieSessionService;
    private TicketService ticketService;

    @Autowired
    public TicketsController(MovieSessionService movieSessionService, TicketService ticketService) {
        this.movieSessionService = movieSessionService;
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "admin/tickets/{sessionID}")
    public String getTicketsForSession(@PathVariable(name = "sessionID") int sessionID, Model model) {
        MovieSession session = movieSessionService.getMovieSession(sessionID);
        model.addAttribute("tickets", ticketService.getTicketsForSession(session));
        model.addAttribute("session", session);
        return "ticket/ticketsPage";
    }

    @RequestMapping(value = "admin/tickets/sell/{ticketID}")
    public String sellTicket(@PathVariable(name = "ticketID") int ticketID) {
        Ticket ticket = ticketService.findByID(ticketID);
        ticketService.sellTicket(ticket);
        return "redirect:/admin/tickets/" + ticket.getMovieSession().getId();
    }

    @RequestMapping(value = "admin/tickets/cancel/{ticketID}")
    public String cancelTicket(@PathVariable(name = "ticketID") int ticketID) {
        Ticket ticket = ticketService.findByID(ticketID);
        ticketService.cancelTicket(ticket);
        return "redirect:/admin/tickets/" + ticket.getMovieSession().getId();
    }

    @PostMapping(value = "/admin/tickets/sellTickets")
    public @ResponseBody
    AjaxResponse sellTickets(@RequestBody TicketDto ticketDto) {
        int sessionID = ticketDto.getSessionID();
        MovieSession session = movieSessionService.getMovieSession(sessionID);
        ticketService.sellTickets(ticketDto, session);
        AjaxResponse resp = new AjaxResponse();
        resp.setMessage("Tickets has been sold");
        return resp;
    }

    @PostMapping(value = "/tickets/reserveTickets")
    public @ResponseBody
    AjaxResponse reserveTickets(@RequestBody TicketDto ticketDto) {
        int sessionID = ticketDto.getSessionID();
        MovieSession session = movieSessionService.getMovieSession(sessionID);
        ticketService.reserveTickets(ticketDto, session);
        AjaxResponse resp = new AjaxResponse();
        resp.setMessage("Tickets has been reserved success");
        return resp;
    }
}
