package barda_lab_5.hotel.services;

import barda_lab_5.hotel.entities.Hotel;
import barda_lab_5.hotel.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
}
