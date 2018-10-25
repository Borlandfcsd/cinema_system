package io.borlandfcsd.cinemasystem.repository;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByMovieSession(MovieSession session);

    Set<Ticket> findByUser(User user);
}
