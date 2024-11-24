package barda_lab_5.hotel.services;

import barda_lab_5.hotel.dto.ProductDto;
import barda_lab_5.hotel.entities.Product;
import barda_lab_5.hotel.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Збереження продукту із завантаженням зображення
    public ProductDto saveProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String filePath = "uploads/" + imageFile.getOriginalFilename();
            File file = new File(filePath);
            imageFile.transferTo(file);
            product.setImagePath(filePath);
        }

        Product savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }

    // Отримання продукту за ID
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    // Отримання продуктів за ціновим діапазоном
    public List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Перетворення сутності в DTO
    private ProductDto mapToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.isOnSale(),
                product.getImagePath()
        );
    }
}
