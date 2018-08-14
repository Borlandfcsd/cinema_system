package io.borlandfcsd.cinemasystem.entity.dto;

import io.borlandfcsd.cinemasystem.entity.comporator.MovieSessionComporator;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

//TODO create class for timeline movies
public class Timeline {
    private Set<MovieSession> timeline;
    private MovieSession lastSession;

    public Timeline(){
        timeline = new TreeSet<>(new MovieSessionComporator());
    }

    public Set<MovieSession> getTimeline() {
        return timeline;
    }

    public void setTimeline(Set<MovieSession> timeline) {
        this.timeline = timeline;
    }

    public MovieSession getLastSession() {
        return lastSession;
    }

    public void setLastSession(MovieSession lastSession) {
        this.lastSession = lastSession;
    }

    public LocalTime getLastSessionBeginTime(){
        return lastSession.getBeginTime();
    }

    public LocalTime getLastSessionEndTime(){
        return lastSession.getEndTime();
    }
}
