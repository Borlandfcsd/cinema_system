package io.borlandfcsd.cinemasystem.service.impl.jpa;

import io.borlandfcsd.cinemasystem.entity.CinemaHall;
import io.borlandfcsd.cinemasystem.entity.PlaceStatus;
import io.borlandfcsd.cinemasystem.entity.dto.TicketDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.repository.TicketRepository;
import io.borlandfcsd.cinemasystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "ticketService")
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public CinemaHall getTicketsForSession(int sessionId) {
        List<Ticket> ticketList = ticketRepository.findByMovieSession(sessionId);
        CinemaHall cinemaHall = new CinemaHall();
        if (!ticketList.isEmpty()) {
            for (Ticket ticket : ticketList) {
                int row = ticket.getRow();
                int place = ticket.getPlace();
                cinemaHall.setPlaceStatus(row, place, ticket);
            }
        }
        return cinemaHall;
    }

    @Transactional
    public void reserveTickets(TicketDto tickets, MovieSession session) {
        for (Ticket ticket : tickets.getTickets()) {
            ticket.setMovieSession(session);
            ticket.setPlaceStatus(PlaceStatus.RESERVED);
            ticketRepository.saveAndFlush(ticket);
        }
    }
}
