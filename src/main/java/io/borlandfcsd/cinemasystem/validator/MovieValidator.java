package io.borlandfcsd.cinemasystem.validator;

import io.borlandfcsd.cinemasystem.entity.dto.MovieDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MovieValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return MovieDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MovieDto movieDto = (MovieDto) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "movie.title", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "movie.duration", "Required");

        if (movieDto.getMovie().getDuration() < 30) {
            errors.rejectValue("movie.duration", "Duplicate.userForm.username");
        }
    }
}
