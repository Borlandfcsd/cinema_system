package io.borlandfcsd.cinemasystem.service;

import io.borlandfcsd.cinemasystem.entity.CinemaHall;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

public interface TicketService {
    CinemaHall getTicketsForSession(MovieSession session);

    void reserveTickets(TicketDto tickets, MovieSession session);
}
