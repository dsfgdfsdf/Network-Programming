package barda_lab_5.hotel.services;

import barda_lab_5.hotel.entities.Reservation;
import barda_lab_5.hotel.entities.Room;
import barda_lab_5.hotel.repositories.ReservationRepository;
import barda_lab_5.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    public ReservationService(ReservationRepository reservationRepository,
                              RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }
    @Transactional
    public Reservation bookRoom(Long roomId, Reservation reservation) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new
                RuntimeException("Room not found"));
        if (!room.isAvailable()) {
            throw new RuntimeException("Room is not available");
        }
        room.setAvailable(false);
        reservation.setRoom(room);
        return reservationRepository.save(reservation);
    }
    public void cancelReservation(Long reservationId) {
        Reservation reservation =
                reservationRepository.findById(reservationId)
                        .orElseThrow(() -> new RuntimeException("Reservation not found"));
                                Room room = reservation.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);
        reservationRepository.delete(reservation);
    }
    public List<Reservation> getReservationsByGuestName(String guestName) {
        return reservationRepository.findByGuestName(guestName);
    }
}
