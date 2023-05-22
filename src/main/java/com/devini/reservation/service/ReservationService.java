package com.devini.reservation.service;

import com.devini.reservation.model.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    Reservation updateReservation(int id, Reservation reservation);
    List<Reservation> getAllReservations();
    Reservation getReservation(int id);
    void deleteReservation(int id);
}
