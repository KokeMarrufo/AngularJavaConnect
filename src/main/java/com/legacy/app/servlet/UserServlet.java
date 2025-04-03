package com.legacy.app.servlet;

import com.legacy.app.model.User;
import com.legacy.app.service.UserService;
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
 * Servlet handling REST operations for User entities
 */
@WebServlet("/api/users/*")
public class UserServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private UserService userService = new UserService();
    private ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * GET method to retrieve users
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Parse path info to determine if request is for single user or all users
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Get all users
            List<User> users = userService.getAllUsers();
            objectMapper.writeValue(response.getWriter(), users);
        } else {
            // Get user by ID
            try {
                Long userId = Long.parseLong(pathInfo.substring(1));
                User user = userService.getUserById(userId);
                
                if (user != null) {
                    objectMapper.writeValue(response.getWriter(), user);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"User not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid user ID format\"}");
            }
        }
    }
    
    /**
     * POST method to create a new user
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
            // Parse JSON into User object
            User user = objectMapper.readValue(buffer.toString(), User.class);
            
            // Add user
            User addedUser = userService.addUser(user);
            
            // Return created user
            response.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(response.getWriter(), addedUser);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid user data\"}");
        }
    }
    
    /**
     * PUT method to update an existing user
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Parse path info to get user ID
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"User ID is required\"}");
            return;
        }
        
        try {
            // Get user ID from path
            Long userId = Long.parseLong(pathInfo.substring(1));
            
            // Read request body
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            
            // Parse JSON into User object
            User user = objectMapper.readValue(buffer.toString(), User.class);
            user.setId(userId);
            
            // Update user
            User updatedUser = userService.updateUser(user);
            
            if (updatedUser != null) {
                objectMapper.writeValue(response.getWriter(), updatedUser);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"User not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid user ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid user data\"}");
        }
    }
    
    /**
     * DELETE method to remove a user
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Parse path info to get user ID
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"User ID is required\"}");
            return;
        }
        
        try {
            // Get user ID from path
            Long userId = Long.parseLong(pathInfo.substring(1));
            
            // Delete user
            boolean deleted = userService.deleteUser(userId);
            
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"User not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid user ID format\"}");
        }
    }
}
