package io.borlandfcsd.cinemasystem.entity.dto;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class TicketDto implements Serializable {
    private int sessionID;
    private List<Ticket> tickets;
}
