package io.borlandfcsd.cinemasystem.service;

import io.borlandfcsd.cinemasystem.entity.dto.Timetable;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.time.LocalDateTime;
import java.util.List;

public interface TimetableService {
    Timetable getTimetable();
}
