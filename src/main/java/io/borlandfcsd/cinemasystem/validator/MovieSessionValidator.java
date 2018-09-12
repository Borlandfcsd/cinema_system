package io.borlandfcsd.cinemasystem.validator;

import io.borlandfcsd.cinemasystem.entity.dto.timetable.Day;
import io.borlandfcsd.cinemasystem.entity.dto.timetable.Timetable;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.MovieSession;
import io.borlandfcsd.cinemasystem.service.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MovieSessionValidator implements Validator {
    MovieSessionService movieSessionService;

    @Autowired
    public MovieSessionValidator(MovieSessionService movieSessionService) {
        this.movieSessionService = movieSessionService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MovieSession.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MovieSession movieSession = (MovieSession) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "movie", "Required");

        Day day = movieSessionService.getDayWithDate(movieSession.getBeginDate().toLocalDate());
        List<MovieSession> freeTimeSessions = day.getTimeline().getFreeTimeList();

        if (freeTimeSessions.isEmpty()) {
            errors.rejectValue("beginDate", "NoFreeTime.movieSession.beginDate");
        }

        if (movieSession.getBeginTime().isBefore(Timetable.CINEMA_OPENING_TIME)) {
            errors.rejectValue("beginDate", "Closed.movieSession.beginDate");
        }

        if (movieSession.getEndTime().isAfter(Timetable.CINEMA_CLOSING_TIME)) {
            errors.rejectValue("beginDate", "Closed.movieSession.beginDate");
        }

        for (MovieSession freeTime : freeTimeSessions) {
            LocalDateTime begin1 = movieSession.getBeginDate();
            LocalDateTime end1 = movieSession.getEndDate();
            LocalDateTime begin2 = freeTime.getBeginDate();
            LocalDateTime end2 = freeTime.getEndDate();
            if (!(isEqualsOrAfter(begin1, begin2) && isEqualsOrBefore(end1, end2))) {
                errors.rejectValue("beginDate", "Overlap.movieSession.beginDate");
            }
        }
    }

    private boolean isEqualsOrAfter(LocalDateTime time1, LocalDateTime time2) {
        return time1.isEqual(time2) || time1.isAfter(time2);
    }

    private boolean isEqualsOrBefore(LocalDateTime time1, LocalDateTime time2) {
        return time1.isEqual(time2) || time1.isBefore(time2);
    }

}
