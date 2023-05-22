package com.devini.reservation;

import com.devini.reservation.model.Reservation;
import com.devini.reservation.service.ReservationService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;


@SpringBootTest
class ReservationApplicationTests {

	@Autowired
	private ReservationService reservationService;

	@Test
	void shouldCreateReservationWithValidDates() {
		Reservation reservation = new Reservation();
		reservation.setName("John Doe");
		reservation.setImage("https://compras.wiki.ufsc.br/images/1/1b/Ok.png");
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 25));

		Reservation createdReservation = reservationService.createReservation(reservation);

		Assertions.assertNotNull(createdReservation);
		Assertions.assertEquals(LocalDate.of(2023, 5, 20), createdReservation.getStartDate());
		Assertions.assertEquals(LocalDate.of(2023, 5, 25), createdReservation.getEndDate());
	}

	@Test
	void shouldNotAllowReservationWithEndDateBeforeStartDate() {
		Reservation reservation = new Reservation();
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 19));

		Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
	}

	@Test
	void shouldNotAllowToCreateReservationWithStartDateEqualsNull() {
		Reservation reservation = new Reservation();
		reservation.setStartDate(null);
		reservation.setEndDate(LocalDate.of(2023, 5, 20));

		Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
	}

	@Test
	void shouldNotAllowToCreateReservationWithEndDateEqualsNull() {
		Reservation reservation = new Reservation();
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(null);

		Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
	}

	@Test
	void shouldNotAllowReservationWithSameStartHourAndEndHour() {
		Reservation reservation = new Reservation();
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 20));
		reservation.setStartHour(LocalTime.of(9, 0));
		reservation.setEndHour(LocalTime.of(9, 0));

		Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
	}

	@Test
	void shouldNotAllowReservationWithEndHourBeforeStartHour() {
		Reservation reservation = new Reservation();
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 20));
		reservation.setStartHour(LocalTime.of(9, 0));
		reservation.setEndHour(LocalTime.of(8, 0));

		Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
	}

	@Test
	void shouldNotAllowReservationWithEndHourEqualsNull() {
		Reservation reservation = new Reservation();
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 20));
		reservation.setStartHour(LocalTime.of(9, 0));
		reservation.setEndHour(null);

		Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
	}

	@Test
	void shouldNotAllowReservationWithStartHourEqualsNull() {
		Reservation reservation = new Reservation();
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 20));
		reservation.setStartHour(null);
		reservation.setEndHour(LocalTime.of(9, 0));

		Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
	}

	@Test
	void shouldUpdateReservationWithValidDates() {
		// Crie uma reserva inicial para ser atualizada
		Reservation reservation = new Reservation();
		reservation.setName("John Doe");
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 25));
		reservation.setImage("https://compras.wiki.ufsc.br/images/1/1b/Ok.png");

		// Crie a reserva inicial no banco de dados e obtenha a reserva criada
		Reservation createdReservation = reservationService.createReservation(reservation);

		// Atualize as datas e a imagem da reserva criada
		createdReservation.setStartDate(LocalDate.of(2023, 6, 1));
		createdReservation.setEndDate(LocalDate.of(2023, 6, 5));
		createdReservation.setImage("https://example.com/new_image.png");

		// Atualize a reserva no banco de dados e obtenha a reserva atualizada
		Reservation updatedReservation = reservationService.updateReservation(createdReservation.getId(), createdReservation);

		// Verifique se as datas e a imagem foram atualizadas corretamente
		Assertions.assertEquals(LocalDate.of(2023, 6, 1), updatedReservation.getStartDate());
		Assertions.assertEquals(LocalDate.of(2023, 6, 5), updatedReservation.getEndDate());
		Assertions.assertEquals("https://example.com/new_image.png", updatedReservation.getImage());
	}



	@Test
	void shouldDeleteReservation() {
		Reservation reservation = new Reservation();
		reservation.setName("John Doe");
		reservation.setStartDate(LocalDate.of(2023, 5, 20));
		reservation.setEndDate(LocalDate.of(2023, 5, 25));

		Reservation createdReservation = reservationService.createReservation(reservation);
		int reservationId = createdReservation.getId();

		reservationService.deleteReservation(reservationId);

		Assertions.assertThrows(RuntimeException.class, () -> {
			reservationService.getReservation(reservationId);
		});
	}
}
