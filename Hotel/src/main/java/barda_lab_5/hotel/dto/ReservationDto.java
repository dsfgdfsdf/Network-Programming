package barda_lab_5.hotel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {
    private Long id;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId; // Ідентифікатор кімнати
}
