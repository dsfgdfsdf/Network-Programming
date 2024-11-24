package barda_lab_5.hotel.controllers.api;

import barda_lab_5.hotel.dto.ReservationDto;
import barda_lab_5.hotel.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationApiController {
    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/book")
    public ReservationDto bookRoom(@RequestParam Long roomId, @RequestBody ReservationDto reservationDTO) {
        return reservationService.bookRoom(roomId, reservationDTO);
    }

    @DeleteMapping("/cancel/{reservationId}")
    public void cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
    }

    @GetMapping("/by-name")
    public List<ReservationDto> getReservationsByGuestName(@RequestParam String guestName) {
        return reservationService.getReservationsByGuestName(guestName);
    }
}
