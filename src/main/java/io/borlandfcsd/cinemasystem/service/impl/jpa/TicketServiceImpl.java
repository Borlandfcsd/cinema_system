package io.borlandfcsd.cinemasystem.service.impl.jpa;

import io.borlandfcsd.cinemasystem.config.state.SecurityState;
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

@Transactional
@Service(value = "ticketService")
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;


    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket findByID(int id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public CinemaHall getCinemaHallSchema(MovieSession session) {
        List<Ticket> ticketList = ticketRepository.findByMovieSession(session);
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

    public void sellTicket(Ticket ticket) {
        ticket.setPlaceStatus(PlaceStatus.SOLD);
        ticketRepository.saveAndFlush(ticket);
    }

    @Override
    public List<Ticket> getTicketsForSession(MovieSession session) {
        return ticketRepository.findByMovieSession(session);
    }

    public void reserveTickets(TicketDto tickets, MovieSession session) {
        for (Ticket ticket : tickets.getTickets()) {
            ticket.setMovieSession(session);
            ticket.setPlaceStatus(PlaceStatus.RESERVED);
            ticket.setUser(SecurityState.getAuthorizedUser());
            ticketRepository.saveAndFlush(ticket);
        }
    }

    @Override
    public void cancelTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    public void sellTickets(TicketDto tickets, MovieSession session) {
        for (Ticket ticket : tickets.getTickets()) {
            ticket.setMovieSession(session);
            ticket.setPlaceStatus(PlaceStatus.SOLD);
            ticket.setUser(SecurityState.getAuthorizedUser());
            ticketRepository.saveAndFlush(ticket);
        }
    }
}
