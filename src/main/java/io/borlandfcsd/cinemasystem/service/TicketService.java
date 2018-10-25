package io.borlandfcsd.cinemasystem.service;

import io.borlandfcsd.cinemasystem.entity.CinemaHall;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;

import java.util.Set;

public interface TicketService {
    CinemaHall getTicketsForSession(MovieSession session);

    Set<Ticket> getTicketsForUser(User user);
    void reserveTickets(TicketDto tickets, MovieSession session);
}
