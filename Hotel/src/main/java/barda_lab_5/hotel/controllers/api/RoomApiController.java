package barda_lab_5.hotel.controllers.api;

import barda_lab_5.hotel.dto.RoomDto;
import barda_lab_5.hotel.services.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {
    private final RoomService roomService;

    public RoomApiController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomDto> getRooms(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return roomService.getRoomsByPriceRange(minPrice, maxPrice);
        } else {
            return roomService.getRoomsByPriceRange(0, Double.MAX_VALUE);
        }
    }
}
