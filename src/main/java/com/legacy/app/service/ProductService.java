package com.legacy.app.service;

import com.legacy.app.model.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class providing product-related operations
 */
public class ProductService {
    
    private static final Map<Long, Product> products = new HashMap<Long, Product>();
    private static Long idCounter = 1L;
    
    static {
        // Initialize with some data
        addProduct(new Product(idCounter++, "Laptop", "High-performance laptop", new BigDecimal("999.99"), 10));
        addProduct(new Product(idCounter++, "Smartphone", "Latest smartphone", new BigDecimal("699.99"), 15));
        addProduct(new Product(idCounter++, "Tablet", "10-inch tablet", new BigDecimal("349.99"), 8));
        addProduct(new Product(idCounter++, "Headphones", "Noise-cancelling headphones", new BigDecimal("199.99"), 20));
    }
    
    /**
     * Get all products
     */
    public List<Product> getAllProducts() {
        return new ArrayList<Product>(products.values());
    }
    
    /**
     * Get product by ID
     */
    public Product getProductById(Long id) {
        return products.get(id);
    }
    
    /**
     * Add a new product
     */
    public static Product addProduct(Product product) {
        if (product.getId() == null) {
            product.setId(idCounter++);
        }
        products.put(product.getId(), product);
        return product;
    }
    
    /**
     * Update an existing product
     */
    public Product updateProduct(Product product) {
        if (product.getId() != null && products.containsKey(product.getId())) {
            products.put(product.getId(), product);
            return product;
        }
        return null;
    }
    
    /**
     * Delete a product by ID
     */
    public boolean deleteProduct(Long id) {
        if (products.containsKey(id)) {
            products.remove(id);
            return true;
        }
        return false;
    }
}
