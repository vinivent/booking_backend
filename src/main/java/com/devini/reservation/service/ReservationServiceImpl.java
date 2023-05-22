package com.devini.reservation.service;

import com.devini.reservation.model.Reservation;
import com.devini.reservation.repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepo reservationRepo;

    @Override
    public Reservation createReservation(Reservation reservation) {
             return reservationRepo.save(reservation);
    }

    @Override
    public Reservation updateReservation(int id, Reservation reservation) {
        Reservation existingReservation = reservationRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Reservation not found with ID: " + id));

        existingReservation.setName(reservation.getName());
        existingReservation.setImage(reservation.getImage());
        existingReservation.setStartDate(reservation.getStartDate());
        existingReservation.setEndDate(reservation.getEndDate());

        return reservationRepo.save(existingReservation);
    }


    @Override
    public Reservation getReservation(int id) {
        return reservationRepo.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found with ID: " +
                id));
    }

    @Override
    public void deleteReservation(int id) {
        Reservation reservation = reservationRepo.findById(id).orElseThrow(() -> new RuntimeException("Reservation " +
                "not found with ID: " + id));
        reservationRepo.delete(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

}
