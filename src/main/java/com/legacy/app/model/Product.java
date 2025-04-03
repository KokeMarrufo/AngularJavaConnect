package com.legacy.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Product model class representing product information
 */
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    
    public Product() {
        // Default constructor required for Jackson
    }
    
    public Product(Long id, String name, String description, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
    
    // Getters and Setters
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
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + 
               ", price=" + price + ", stock=" + stock + "]";
    }
}
