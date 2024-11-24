package barda_lab_5.hotel.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean isOnSale;
    @Column(name = "image_path", length = 500)
    private String imagePath;
}
