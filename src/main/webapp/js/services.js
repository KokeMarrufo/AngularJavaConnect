'use strict';

/**
 * User Service
 */
angular.module('legacyApp').factory('UserService', ['$http', 
    function($http) {
        var baseUrl = 'api/users';
        
        return {
            // Get all users
            getUsers: function() {
                return $http.get(baseUrl);
            },
            
            // Get user by ID
            getUser: function(id) {
                return $http.get(baseUrl + '/' + id);
            },
            
            // Add new user
            addUser: function(user) {
                return $http.post(baseUrl, user);
            },
            
            // Update existing user
            updateUser: function(user) {
                return $http.put(baseUrl + '/' + user.id, user);
            },
            
            // Delete user
            deleteUser: function(id) {
                return $http.delete(baseUrl + '/' + id);
            }
        };
    }
]);

/**
 * Product Service
 */
angular.module('legacyApp').factory('ProductService', ['$http', 
    function($http) {
        var baseUrl = 'api/products';
        
        return {
            // Get all products
            getProducts: function() {
                return $http.get(baseUrl);
            },
            
            // Get product by ID
            getProduct: function(id) {
                return $http.get(baseUrl + '/' + id);
            },
            
            // Add new product
            addProduct: function(product) {
                return $http.post(baseUrl, product);
            },
            
            // Update existing product
            updateProduct: function(product) {
                return $http.put(baseUrl + '/' + product.id, product);
            },
            
            // Delete product
            deleteProduct: function(id) {
                return $http.delete(baseUrl + '/' + id);
            }
        };
    }
]);
