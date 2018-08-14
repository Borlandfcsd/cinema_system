package io.borlandfcsd.cinemasystem.entity.comporator;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.util.Comparator;

public class MovieSessionComporator implements Comparator<MovieSession> {
    @Override
    public int compare(MovieSession session1, MovieSession session2) {
        return session1.getBeginDate().compareTo(session2.getBeginDate());
    }
}
