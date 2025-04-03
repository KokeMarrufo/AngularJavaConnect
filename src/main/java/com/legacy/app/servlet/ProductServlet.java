package com.legacy.app.servlet;

import com.legacy.app.model.Product;
import com.legacy.app.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Servlet handling REST operations for Product entities
 * Mapping for this servlet is defined in web.xml
 */
public class ProductServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private ProductService productService = new ProductService();
    private ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * GET method to retrieve products
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Parse path info to determine if request is for single product or all products
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Get all products
            List<Product> products = productService.getAllProducts();
            objectMapper.writeValue(response.getWriter(), products);
        } else {
            // Get product by ID
            try {
                Long productId = Long.parseLong(pathInfo.substring(1));
                Product product = productService.getProductById(productId);
                
                if (product != null) {
                    objectMapper.writeValue(response.getWriter(), product);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Product not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid product ID format\"}");
            }
        }
    }
    
    /**
     * POST method to create a new product
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Read request body
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        
        try {
            // Parse JSON into Product object
            Product product = objectMapper.readValue(buffer.toString(), Product.class);
            
            // Add product
            Product addedProduct = productService.addProduct(product);
            
            // Return created product
            response.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(response.getWriter(), addedProduct);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid product data\"}");
        }
    }
    
    /**
     * PUT method to update an existing product
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Parse path info to get product ID
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Product ID is required\"}");
            return;
        }
        
        try {
            // Get product ID from path
            Long productId = Long.parseLong(pathInfo.substring(1));
            
            // Read request body
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            
            // Parse JSON into Product object
            Product product = objectMapper.readValue(buffer.toString(), Product.class);
            product.setId(productId);
            
            // Update product
            Product updatedProduct = productService.updateProduct(product);
            
            if (updatedProduct != null) {
                objectMapper.writeValue(response.getWriter(), updatedProduct);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Product not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid product ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid product data\"}");
        }
    }
    
    /**
     * DELETE method to remove a product
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Parse path info to get product ID
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Product ID is required\"}");
            return;
        }
        
        try {
            // Get product ID from path
            Long productId = Long.parseLong(pathInfo.substring(1));
            
            // Delete product
            boolean deleted = productService.deleteProduct(productId);
            
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Product not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid product ID format\"}");
        }
    }
}
