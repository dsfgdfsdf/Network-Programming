package barda_lab_5.hotel.services;


import barda_lab_5.hotel.entities.Product;
import barda_lab_5.hotel.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product saveProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            // Зберігаємо файл у локальній директорії
            String filePath = "uploads/" + imageFile.getOriginalFilename();
            File file = new File(filePath);
            imageFile.transferTo(file);

            // Зберігаємо шлях до файлу в базу даних
            product.setImagePath(filePath);
        }
        return productRepository.save(product);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}