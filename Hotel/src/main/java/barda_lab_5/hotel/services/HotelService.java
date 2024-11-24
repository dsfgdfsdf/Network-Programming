package barda_lab_5.hotel.services;


import barda_lab_5.hotel.dto.HotelDto;
import barda_lab_5.hotel.entities.Hotel;
import barda_lab_5.hotel.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // Отримання всіх готелів як DTO
    public List<HotelDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotel -> new HotelDto(hotel.getId(), hotel.getName(), hotel.getLocation()))
                .collect(Collectors.toList());
    }
}
