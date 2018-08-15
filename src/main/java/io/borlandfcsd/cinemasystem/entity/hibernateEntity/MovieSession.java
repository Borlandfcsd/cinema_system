package io.borlandfcsd.cinemasystem.entity.hibernateEntity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "timetable", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class MovieSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "begin_date", unique = true, nullable = false)
    private LocalDateTime beginDate;
    @Transient
    private LocalDateTime endDate;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public void setBeginDate(String beginDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.beginDate = LocalDateTime.parse(beginDate, formatter);
    }

    public LocalDateTime getEndDate() {
        endDate = beginDate.plusMinutes(movie.getDuration());
        return endDate;
    }

    public LocalTime getBeginTime() {
        return beginDate.toLocalTime();
    }

    public LocalTime getEndTime() {
        getEndDate();
        return endDate.toLocalTime();
    }

    @Override
    public String toString() {
        return "MovieSession{" +
                "id=" + id +
                ", movie=" + movie +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                '}';
    }
}
