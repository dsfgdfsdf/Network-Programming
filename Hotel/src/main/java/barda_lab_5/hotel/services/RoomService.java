package barda_lab_5.hotel.services;

import barda_lab_5.hotel.entities.Room;
import barda_lab_5.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRoomsByPriceRange(double minPrice, double maxPrice) {
        return roomRepository.findByPriceRangeAndAvailable(minPrice, maxPrice);
    }
}
