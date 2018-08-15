package io.borlandfcsd.cinemasystem.service.impl;

import io.borlandfcsd.cinemasystem.dao.GenericDao;
import io.borlandfcsd.cinemasystem.entity.CinemaHall;
import io.borlandfcsd.cinemasystem.entity.PlaceStatus;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketServiceImpl implements TicketService {

    private static TicketServiceImpl instance;
    @Autowired
    private GenericDao ticketDao;

    private TicketServiceImpl() {

    }

    @SuppressWarnings("unchecked")
    public CinemaHall getTickets(int id) {
        List<Ticket> tickets = ticketDao.getEntitiesByColumnName("session_id", id);
        CinemaHall cinemaHall = new CinemaHall();
        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                int row = ticket.getRow();
                int place = ticket.getPlace();
                cinemaHall.setPlaceStatus(row, place, ticket);
            }
        }
        return cinemaHall;
    }

    @SuppressWarnings("unchecked")
    public void reserveTickets(TicketDto tickets, MovieSession session) {
        for (Ticket ticket : tickets.getTickets()) {
            ticket.setMovieSession(session);
            ticket.setPlaceStatus(PlaceStatus.RESERVED);
            ticketDao.addEntity(ticket);
        }
    }

    private void setTicketDao(GenericDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public static TicketServiceImpl getInstance() {
        if (instance == null) {
            instance = new TicketServiceImpl();
        }
        return instance;
    }
}
