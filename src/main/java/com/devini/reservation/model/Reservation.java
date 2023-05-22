package com.devini.reservation.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startHour;
    private LocalTime endHour;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name == "") {
            throw new IllegalArgumentException("The name cannot be null or empty.");
        }
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image == null) {
            throw new IllegalArgumentException("The profile picture cannot be null.");
        }
        this.image = image;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("The start date cannot be null.");
        }
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("The end date cannot be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("The end date cannot be before the start date.");
        }
        this.endDate = endDate;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        if (startHour == null) {
            throw new IllegalArgumentException("The start hour cannot be null.");
        }
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public boolean isSameDay() {
        return startDate.isEqual(endDate);
    }

    public void setEndHour(LocalTime endHour) {
        if (endHour == null) {
            throw new IllegalArgumentException("The end date cannot be null.");
        }
        if (isSameDay() && endHour.isBefore(startHour)) {
            throw new IllegalArgumentException("The end hour cannot be before the start hour.");
        }
        if (endHour.equals(startHour)) {
            throw new IllegalArgumentException("The end date cannot be the same as the start date.");
        }
        this.endHour = endHour;
    }
}
