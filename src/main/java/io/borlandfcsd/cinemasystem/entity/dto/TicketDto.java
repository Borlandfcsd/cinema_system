package io.borlandfcsd.cinemasystem.entity.dto;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;

import java.util.List;

public class TicketDto {
    private int sessionID;
    private List<Ticket> tickets;


    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
