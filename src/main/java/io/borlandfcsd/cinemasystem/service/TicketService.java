package io.borlandfcsd.cinemasystem.service;

import io.borlandfcsd.cinemasystem.entity.CinemaHall;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;

import java.util.List;

public interface TicketService {
    CinemaHall getCinemaHallSchema(MovieSession session);

    List<Ticket> getTicketsForSession(MovieSession session);

    Ticket findByID(int id);

    void sellTicket(Ticket ticket);

    void cancelTicket(Ticket ticket);
    void reserveTickets(TicketDto tickets, MovieSession session);

    void sellTickets(TicketDto tickets, MovieSession session);
}
