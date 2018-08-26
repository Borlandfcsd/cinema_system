package io.borlandfcsd.cinemasystem.entity.hibernateEntity;


import io.borlandfcsd.cinemasystem.entity.PlaceStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String email;
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private MovieSession movieSession;
    @Column(nullable = false)
    private int row;
    @Column(nullable = false)
    private int place;
    @Column(nullable = false)
    private PlaceStatus placeStatus;
    @Column
    private double price;

    public int getId() {
        return id;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public PlaceStatus getPlaceStatus() {
        return placeStatus;
    }

    public void setPlaceStatus(PlaceStatus placeStatus) {
        this.placeStatus = placeStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
