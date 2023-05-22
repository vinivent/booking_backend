package com.devini.reservation.controller;

import com.devini.reservation.model.Reservation;
import com.devini.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/addReservations")
    @CrossOrigin(origins = "*")
    public String createReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);
        return "Reservation created successfully!"  ;
    }

    @GetMapping("/getReservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
        return "Reservation deleted successfully!";
    }

    @PutMapping("/updateReservation/{id}")
    public String updateReservation(@PathVariable int id, @RequestBody Reservation reservation) {
        reservationService.updateReservation(id, reservation);
        return "Reservation updated successfully!";
    }
}
