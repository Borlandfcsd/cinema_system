package io.borlandfcsd.cinemasystem.entity.dto;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

//TODO create class for timeline movies
public class Timeline {
    private static final int MIN_FILM_DURATION = 110;
    private static final LocalTime MAX_SESSION_BEGIN_TIME = LocalTime.of(20, 0);
    private List<MovieSession> timeline;


    public Timeline(List<MovieSession> movieSessions) {
        this.timeline = new ArrayList<>(movieSessions);
        createTimeline(movieSessions);
    }


    public void createTimeline(List<MovieSession> sessions) {
        List<MovieSession> immutableList = ImmutableList.copyOf(sessions);
        for (int current = 0; current < immutableList.size(); current++) {

            int next = current + 1;


            MovieSession currentSession = Iterables.get(immutableList, current, null);
            MovieSession nextSession = Iterables.get(immutableList, next, null);

            long freeTime = getPeriod(currentSession, nextSession);
            if (freeTime >= MIN_FILM_DURATION) {
                current++;
                Movie emptyMovie = createEmptyMovie(freeTime);
                MovieSession emptySession = createEmptySession(emptyMovie, currentSession.getEndDate());
                timeline.add(next, emptySession);
            }
        }
    }

    private long getPeriod(MovieSession session1, MovieSession session2) {
        LocalTime time1 = session1.getEndTime();
        LocalTime time2;

        if (session2 != null) {
            time2 = session2.getBeginTime();
        } else {
            time2 = MAX_SESSION_BEGIN_TIME;
        }
        long range = ChronoUnit.MINUTES.between(time1, time2);

        return range - (Timetable.BREAK_BETWEEN_SESSIONS * 2);
    }

    private Movie createEmptyMovie(long minutes) {
        Movie freeTime = new Movie();
        freeTime.setTitle("free time");
        freeTime.setDuration((int) minutes);
        return freeTime;
    }

    private MovieSession createEmptySession(Movie movie, LocalDateTime prevSessionEndDate) {
        MovieSession freeTime = new MovieSession();
        freeTime.setMovie(movie);
        freeTime.setBeginDate(prevSessionEndDate.plusMinutes(Timetable.BREAK_BETWEEN_SESSIONS));

        return freeTime;
    }

    public List<MovieSession> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<MovieSession> timeline) {
        this.timeline = timeline;
    }
}
