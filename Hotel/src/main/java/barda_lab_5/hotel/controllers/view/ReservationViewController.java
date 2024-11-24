package barda_lab_5.hotel.controllers.view;

import barda_lab_5.hotel.dto.ReservationDto;
import barda_lab_5.hotel.services.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationViewController {
    private final ReservationService reservationService;

    public ReservationViewController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/book")
    public String showBookingForm(@RequestParam Long roomId, Model model) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("reservation", new ReservationDto());
        return "reservation-form";
    }

    @PostMapping("/book")
    public String bookRoom(@RequestParam Long roomId,
                           @ModelAttribute ReservationDto reservationDTO,
                           RedirectAttributes redirectAttributes) {
        try {
            reservationService.bookRoom(roomId, reservationDTO);
            redirectAttributes.addFlashAttribute("message", "Room successfully booked!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/rooms";
    }

    @GetMapping("/by-name")
    public String getReservationsByGuestName(@RequestParam String guestName, Model model) {
        List<ReservationDto> reservations = reservationService.getReservationsByGuestName(guestName);
        model.addAttribute("reservations", reservations);
        model.addAttribute("guestName", guestName);
        return "reservation-list";
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search-reservation";
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam Long reservationId,
                                    RedirectAttributes redirectAttributes) {
        try {
            reservationService.cancelReservation(reservationId);
            redirectAttributes.addFlashAttribute("message", "Reservation successfully canceled!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/rooms";
    }
}
