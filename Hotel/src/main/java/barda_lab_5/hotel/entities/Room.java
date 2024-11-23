package barda_lab_5.hotel.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String type;
    private double pricePerNight;
    private boolean isAvailable;
    @ManyToOne
    private Hotel hotel;
}
