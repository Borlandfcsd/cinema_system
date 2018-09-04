package io.borlandfcsd.cinemasystem.entity.comparator;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.util.Comparator;

public class MovieSessionComparator implements Comparator<MovieSession> {
    @Override
    public int compare(MovieSession o1, MovieSession o2) {
        return o1.getBeginDate().compareTo(o2.getBeginDate());
    }
}
