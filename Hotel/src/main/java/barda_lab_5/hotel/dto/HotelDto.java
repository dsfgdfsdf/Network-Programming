package barda_lab_5.hotel.dto;

public class HotelDto {
    private Long id;
    private String name;
    private String address;

    // Конструктори
    public HotelDto() {}
    public HotelDto(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // Гетери і сетери
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return getLocation();
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
