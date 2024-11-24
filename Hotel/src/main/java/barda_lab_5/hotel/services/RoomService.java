package barda_lab_5.hotel.services;

import barda_lab_5.hotel.dto.RoomDto;
import barda_lab_5.hotel.entities.Room;
import barda_lab_5.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomDto> getRoomsByPriceRange(double minPrice, double maxPrice) {
        List<Room> rooms = roomRepository.findByPriceRangeAndAvailable(minPrice, maxPrice);
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RoomDto convertToDTO(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setType(room.getType());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setAvailable(room.isAvailable());
        dto.setHotelId(room.getHotel() != null ? room.getHotel().getId() : null);
        return dto;
    }
}
