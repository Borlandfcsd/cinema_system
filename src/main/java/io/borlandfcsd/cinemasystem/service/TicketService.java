package io.borlandfcsd.cinemasystem.service;

import io.borlandfcsd.cinemasystem.entity.CinemaHall;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

public interface TicketService {
    CinemaHall getTicketsForSession(int sessionId);

    void reserveTickets(TicketDto tickets, MovieSession session);
}
