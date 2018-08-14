package io.borlandfcsd.cinemasystem.entity.hibernateEntity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "movies", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "title")
})
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false, length = 200)
    private String title;
    @Column
    private String part;
    @Column
    private int year;
    @Column(nullable = false)
    private int duration;
    @Column
    private String poster;
    @Column(columnDefinition = "TEXT")
    private String discription;
    @Column
    private String stars;
    @Column
    private String director;
    @Column
    private String rating;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                duration == movie.duration &&
                Objects.equals(title, movie.title) &&
                Objects.equals(poster, movie.poster);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, duration, poster);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", poster='" + poster + '\'' +
                '}';
    }
}
