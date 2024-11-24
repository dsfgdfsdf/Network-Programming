package barda_lab_5.hotel.controllers.view;

import barda_lab_5.hotel.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rooms")
public class RoomViewController {
    private final RoomService roomService;

    public RoomViewController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String showRoomsAsHtml(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {
        model.addAttribute("rooms", roomService.getRoomsByPriceRange(
                minPrice != null ? minPrice : 0,
                maxPrice != null ? maxPrice : Double.MAX_VALUE
        ));
        return "room-list";
    }
}
