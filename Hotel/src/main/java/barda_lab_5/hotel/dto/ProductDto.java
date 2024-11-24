package barda_lab_5.hotel.dto;

public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean isOnSale;
    private String imagePath;

    // Конструктори
    public ProductDto() {}

    public ProductDto(Long id, String name, String description, double price, boolean isOnSale, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isOnSale = isOnSale;
        this.imagePath = imagePath;
    }


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
