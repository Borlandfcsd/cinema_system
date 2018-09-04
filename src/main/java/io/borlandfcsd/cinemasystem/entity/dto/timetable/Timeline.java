package io.borlandfcsd.cinemasystem.entity.dto.timetable;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Timeline {
    private static final int MIN_FREE_TIME_PERIOD = 90;
    private List<MovieSession> timeline;
    private List<MovieSession> freeTimeList;


    public Timeline(List<MovieSession> movieSessions) {
        this.timeline = new ArrayList<>(movieSessions);
        this.freeTimeList = new ArrayList<>();
        install(movieSessions);
    }


    public void install(List<MovieSession> sessions) {
        if (sessions.isEmpty()) {
            MovieSession freeTime = createTimelineWithoutSession();
            timeline.add(freeTime);
            freeTimeList.add(freeTime);
        } else {
            searchFreeTime(sessions);
        }
    }

    private MovieSession createTimelineWithoutSession() {
        long duration = ChronoUnit.MINUTES.between(Timetable.CINEMA_OPENING_TIME, Timetable.CINEMA_CLOSING_TIME);
        Movie freeMovie = createEmptyMovie(duration);
        MovieSession freeTime = new MovieSession();
        freeTime.setMovie(freeMovie);
        freeTime.setBeginDate(LocalDateTime.now().with(Timetable.CINEMA_OPENING_TIME));
        freeTime.setEndDate(LocalDateTime.now().with(Timetable.CINEMA_CLOSING_TIME));
        return freeTime;
    }

    private void searchFreeTime(List<MovieSession> sessions) {
        List<MovieSession> immutableList = ImmutableList.copyOf(sessions);
        for (int session = 0; session < immutableList.size(); session++) {
            int nextSession = session + 1;
            MovieSession defaultSession  = new MovieSession();
            defaultSession.setId(1);
            MovieSession session1 = Iterables.get(immutableList, session, defaultSession);
            MovieSession session2 = Iterables.get(immutableList, nextSession, defaultSession);
            long freeTime = getPeriod(session1, session2);
            if (freeTime >= MIN_FREE_TIME_PERIOD) {
                session++;
                MovieSession emptySession = createEmptySession(freeTime, session1.getEndDate());
                timeline.add(nextSession, emptySession);
                freeTimeList.add(emptySession);
            }
        }
    }

    private long getPeriod(MovieSession session1, MovieSession session2) {
        LocalTime time1 = session1.getEndTime();
        LocalTime time2 = (session2.getId() == 1) ? Timetable.MAX_SESSION_BEGIN_TIME : session2.getBeginTime();
        long range = ChronoUnit.MINUTES.between(time1, time2);
        return range - (Timetable.BREAK_BETWEEN_SESSIONS * 2);
    }

    private MovieSession createEmptySession(long movieDuration, LocalDateTime beginTime) {
        MovieSession freeTime = new MovieSession();
        freeTime.setMovie(createEmptyMovie(movieDuration));
        freeTime.setBeginDate(beginTime.plusMinutes(Timetable.BREAK_BETWEEN_SESSIONS));
        return freeTime;
    }

    private Movie createEmptyMovie(long minutes) {
        Movie freeTime = new Movie();
        freeTime.setTitle("free time");
        freeTime.setDuration((int) minutes);
        return freeTime;
    }
}
