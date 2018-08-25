package io.borlandfcsd.cinemasystem.repository;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
