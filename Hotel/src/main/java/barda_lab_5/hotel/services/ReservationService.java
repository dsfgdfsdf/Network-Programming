package barda_lab_5.hotel.services;

import barda_lab_5.hotel.dto.ReservationDto;
import barda_lab_5.hotel.entities.Reservation;
import barda_lab_5.hotel.entities.Room;
import barda_lab_5.hotel.repositories.ReservationRepository;
import barda_lab_5.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public ReservationDto bookRoom(Long roomId, ReservationDto reservationDTO) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new
                RuntimeException("Room not found"));
        if (!room.isAvailable()) {
            throw new RuntimeException("Room is not available");
        }
        room.setAvailable(false);
        Reservation reservation = new Reservation();
        reservation.setGuestName(reservationDTO.getGuestName());
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        reservation.setRoom(room);

        reservation = reservationRepository.save(reservation);
        return convertToDTO(reservation);
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation =
                reservationRepository.findById(reservationId)
                        .orElseThrow(() -> new RuntimeException("Reservation not found"));
        Room room = reservation.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);
        reservationRepository.delete(reservation);
    }

    public List<ReservationDto> getReservationsByGuestName(String guestName) {
        List<Reservation> reservations = reservationRepository.findByGuestName(guestName);
        return reservations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ReservationDto convertToDTO(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setGuestName(reservation.getGuestName());
        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckOutDate(reservation.getCheckOutDate());
        dto.setRoomId(reservation.getRoom() != null ? reservation.getRoom().getId() : null);
        return dto;
    }
}
