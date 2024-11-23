package barda_lab_5.hotel.controllers;

import barda_lab_5.hotel.entities.Room;
import barda_lab_5.hotel.services.RoomService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Room> getRoomsAsJson(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return roomService.getRoomsByPriceRange(minPrice, maxPrice);
        } else {
            return roomService.getRoomsByPriceRange(0, Double.MAX_VALUE);
        }
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String showRoomsAsHtml(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {
        List<Room> rooms;

        if (minPrice != null && maxPrice != null) {
            rooms = roomService.getRoomsByPriceRange(minPrice, maxPrice);
        } else {
            rooms = roomService.getRoomsByPriceRange(0, Double.MAX_VALUE);
        }

        model.addAttribute("rooms", rooms);
        return "room-list";
    }
}
