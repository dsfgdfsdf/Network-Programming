package barda_lab_5.hotel.repositories;

import barda_lab_5.hotel.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.pricePerNight BETWEEN :minPrice AND :maxPrice AND r.isAvailable = true")
    List<Room> findByPriceRangeAndAvailable(
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice
    );
}
