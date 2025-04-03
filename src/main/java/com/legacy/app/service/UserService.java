package com.legacy.app.service;

import com.legacy.app.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class providing user-related operations
 */
public class UserService {
    
    private static final Map<Long, User> users = new HashMap<Long, User>();
    private static Long idCounter = 1L;
    
    static {
        // Initialize with some data
        addUser(new User(idCounter++, "admin", "admin@example.com", "Administrator"));
        addUser(new User(idCounter++, "john", "john@example.com", "User"));
        addUser(new User(idCounter++, "jane", "jane@example.com", "Manager"));
    }
    
    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return new ArrayList<User>(users.values());
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(Long id) {
        return users.get(id);
    }
    
    /**
     * Add a new user
     */
    public static User addUser(User user) {
        if (user.getId() == null) {
            user.setId(idCounter++);
        }
        users.put(user.getId(), user);
        return user;
    }
    
    /**
     * Update an existing user
     */
    public User updateUser(User user) {
        if (user.getId() != null && users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        }
        return null;
    }
    
    /**
     * Delete a user by ID
     */
    public boolean deleteUser(Long id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return true;
        }
        return false;
    }
}
