package barda_lab_5.hotel.controllers.api;

import barda_lab_5.hotel.dto.HotelDto;
import barda_lab_5.hotel.services.HotelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelApiController {
    private final HotelService hotelService;

    public HotelApiController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<HotelDto> getAllHotels() {
        return hotelService.getAllHotels();
    }
}
