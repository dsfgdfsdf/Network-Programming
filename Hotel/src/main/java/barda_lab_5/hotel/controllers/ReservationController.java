package barda_lab_5.hotel.controllers;

import barda_lab_5.hotel.entities.Reservation;
import barda_lab_5.hotel.services.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Form for creating a reservation
    @GetMapping("/book")
    public String showBookingForm(@RequestParam Long roomId, Model model) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("reservation", new Reservation());
        return "reservation-form";
    }

    // Reservation creation processing
    @PostMapping("/book")
    public String bookRoom(@RequestParam Long roomId,
                           @ModelAttribute Reservation reservation,
                           RedirectAttributes redirectAttributes) {
        try {
            reservationService.bookRoom(roomId, reservation);
            redirectAttributes.addFlashAttribute("message", "Room successfully booked!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/rooms"; // Return to the number list page
    }
    @GetMapping("/by-name")
    public String getReservationsByGuestName(@RequestParam String guestName, Model model) {
        List<Reservation> reservations = reservationService.getReservationsByGuestName(guestName);
        model.addAttribute("reservations", reservations);
        model.addAttribute("guestName", guestName);
        return "reservation-list";
    }
    // Show the form for entering the username
    @GetMapping("/search")
    public String showSearchForm() {
        return "search-reservation"; // Returns the name of the HTML template
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
        return "redirect:/rooms"; // Return to the list of numbers
    }
}
