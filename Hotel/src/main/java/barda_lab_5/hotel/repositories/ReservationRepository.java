package barda_lab_5.hotel.repositories;

import barda_lab_5.hotel.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

        List<Reservation> findByGuestName(String guestName);

}