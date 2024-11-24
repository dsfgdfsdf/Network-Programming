package barda_lab_5.hotel.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @ManyToOne
    private Room room;
}
