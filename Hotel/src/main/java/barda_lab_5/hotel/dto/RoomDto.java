package barda_lab_5.hotel.dto;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String roomNumber;
    private String type;
    private double pricePerNight;
    private boolean isAvailable;
    private Long hotelId;
}
