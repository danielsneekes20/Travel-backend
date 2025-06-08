package com.Sneekes.backend.entity;

import jakarta.persistence.*;

import java.util.Optional;
@Entity
@Table(name = "favorite_stations")
public class FavoriteStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")  // foreign key column name
    private User user;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    // Constructors
    public FavoriteStation() {}

    // Getters and Setters
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    public Long getId() {
        return id;
    }
}


