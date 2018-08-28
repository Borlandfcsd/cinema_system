package io.borlandfcsd.cinemasystem.entity.hibernateEntity;


import lombok.Builder;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "movies")
@Data
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
}
