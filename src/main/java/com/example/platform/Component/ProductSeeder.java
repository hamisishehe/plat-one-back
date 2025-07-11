package com.example.platform.Component;


import com.example.platform.Model.ProductModel;
import com.example.platform.Repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSeeder {

    private final ProductRepository productRepository;

    public ProductSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void seedProducts() {
        if (productRepository.count() == 0) { // Prevent duplicate seeding
            List<ProductModel> products = new ArrayList<>();

            products.add(createProduct("Laptop HP EliteBook", 1200.0));
            products.add(createProduct("Dell Inspiron Laptop", 950.0));
            products.add(createProduct("Samsung Galaxy S24", 850.0));
            products.add(createProduct("iPhone 14 Pro", 1250.0));
            products.add(createProduct("Bluetooth Headphones", 80.0));
            products.add(createProduct("Wireless Mouse", 20.0));
            products.add(createProduct("Mechanical Keyboard", 75.0));
            products.add(createProduct("External Hard Drive 1TB", 60.0));
            products.add(createProduct("USB-C Charger", 25.0));
            products.add(createProduct("Smart Watch Fitbit", 150.0));
            products.add(createProduct("Canon DSLR Camera", 700.0));
            products.add(createProduct("Tripod Stand", 35.0));
            products.add(createProduct("32 inch LED Monitor", 180.0));
            products.add(createProduct("HDMI Cable", 10.0));
            products.add(createProduct("Gaming Chair", 220.0));
            products.add(createProduct("Office Desk", 150.0));
            products.add(createProduct("Portable Speaker", 45.0));
            products.add(createProduct("Power Bank 20000mAh", 30.0));
            products.add(createProduct("Wireless Earbuds", 50.0));
            products.add(createProduct("Graphics Tablet", 120.0));

            productRepository.saveAll(products);
            System.out.println("✅ Seeded 20 real products successfully.");
        } else {
            System.out.println("ℹ️ Products already seeded.");
        }
    }

    private ProductModel createProduct(String name, Double price) {
        ProductModel product = new ProductModel();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
