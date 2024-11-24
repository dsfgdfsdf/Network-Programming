package barda_lab_5.hotel.controllers.view;

import barda_lab_5.hotel.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotels")
public class HotelViewController {
    private final HotelService hotelService;

    public HotelViewController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public String getHotelsPage(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "hotels"; // Ім'я HTML-шаблону
    }
}
