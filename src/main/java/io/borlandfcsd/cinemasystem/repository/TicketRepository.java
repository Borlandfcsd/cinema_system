package io.borlandfcsd.cinemasystem.repository;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
}
