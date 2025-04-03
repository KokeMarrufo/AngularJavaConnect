'use strict';

/**
 * Home Controller
 */
angular.module('legacyApp').controller('HomeController', ['$scope',
    function($scope) {
        $scope.title = 'Welcome to the Legacy AngularJS Servlet Application';
        $scope.description = 'This application demonstrates the integration of AngularJS with Java Servlet backend technology.';
        
        $scope.features = [
            'AngularJS 1.x frontend',
            'Java Servlet backend',
            'RESTful API integration',
            'Bootstrap UI',
            'SPA architecture'
        ];
    }
]);

/**
 * Users Controller
 */
angular.module('legacyApp').controller('UsersController', ['$scope', 'UserService',
    function($scope, UserService) {
        $scope.users = [];
        $scope.newUser = {};
        $scope.editingUser = null;
        $scope.loading = true;
        
        // Load all users
        function loadUsers() {
            $scope.loading = true;
            UserService.getUsers()
                .then(function(response) {
                    $scope.users = response.data;
                    $scope.loading = false;
                })
                .catch(function(error) {
                    console.error('Error loading users:', error);
                    $scope.loading = false;
                });
        }
        
        // Initialize controller
        loadUsers();
        
        // Add a new user
        $scope.addUser = function() {
            if (!$scope.newUser.username || !$scope.newUser.email) {
                $scope.error = 'Username and email are required';
                return;
            }
            
            UserService.addUser($scope.newUser)
                .then(function(response) {
                    $scope.users.push(response.data);
                    $scope.newUser = {};
                    $scope.error = null;
                })
                .catch(function(error) {
                    $scope.error = 'Error adding user: ' + error.statusText;
                });
        };
        
        // Edit user
        $scope.editUser = function(user) {
            $scope.editingUser = angular.copy(user);
        };
        
        // Cancel editing
        $scope.cancelEdit = function() {
            $scope.editingUser = null;
        };
        
        // Save edited user
        $scope.saveUser = function() {
            UserService.updateUser($scope.editingUser)
                .then(function(response) {
                    // Find and replace the user in the array
                    for (var i = 0; i < $scope.users.length; i++) {
                        if ($scope.users[i].id === response.data.id) {
                            $scope.users[i] = response.data;
                            break;
                        }
                    }
                    $scope.editingUser = null;
                })
                .catch(function(error) {
                    $scope.error = 'Error updating user: ' + error.statusText;
                });
        };
        
        // Delete user
        $scope.deleteUser = function(userId) {
            if (confirm('Are you sure you want to delete this user?')) {
                UserService.deleteUser(userId)
                    .then(function() {
                        // Remove the user from the array
                        $scope.users = $scope.users.filter(function(user) {
                            return user.id !== userId;
                        });
                    })
                    .catch(function(error) {
                        $scope.error = 'Error deleting user: ' + error.statusText;
                    });
            }
        };
    }
]);

/**
 * Products Controller
 */
angular.module('legacyApp').controller('ProductsController', ['$scope', 'ProductService',
    function($scope, ProductService) {
        $scope.products = [];
        $scope.newProduct = {};
        $scope.editingProduct = null;
        $scope.loading = true;
        
        // Load all products
        function loadProducts() {
            $scope.loading = true;
            ProductService.getProducts()
                .then(function(response) {
                    $scope.products = response.data;
                    $scope.loading = false;
                })
                .catch(function(error) {
                    console.error('Error loading products:', error);
                    $scope.loading = false;
                });
        }
        
        // Initialize controller
        loadProducts();
        
        // Add a new product
        $scope.addProduct = function() {
            if (!$scope.newProduct.name || !$scope.newProduct.price) {
                $scope.error = 'Name and price are required';
                return;
            }
            
            ProductService.addProduct($scope.newProduct)
                .then(function(response) {
                    $scope.products.push(response.data);
                    $scope.newProduct = {};
                    $scope.error = null;
                })
                .catch(function(error) {
                    $scope.error = 'Error adding product: ' + error.statusText;
                });
        };
        
        // Edit product
        $scope.editProduct = function(product) {
            $scope.editingProduct = angular.copy(product);
        };
        
        // Cancel editing
        $scope.cancelEdit = function() {
            $scope.editingProduct = null;
        };
        
        // Save edited product
        $scope.saveProduct = function() {
            ProductService.updateProduct($scope.editingProduct)
                .then(function(response) {
                    // Find and replace the product in the array
                    for (var i = 0; i < $scope.products.length; i++) {
                        if ($scope.products[i].id === response.data.id) {
                            $scope.products[i] = response.data;
                            break;
                        }
                    }
                    $scope.editingProduct = null;
                })
                .catch(function(error) {
                    $scope.error = 'Error updating product: ' + error.statusText;
                });
        };
        
        // Delete product
        $scope.deleteProduct = function(productId) {
            if (confirm('Are you sure you want to delete this product?')) {
                ProductService.deleteProduct(productId)
                    .then(function() {
                        // Remove the product from the array
                        $scope.products = $scope.products.filter(function(product) {
                            return product.id !== productId;
                        });
                    })
                    .catch(function(error) {
                        $scope.error = 'Error deleting product: ' + error.statusText;
                    });
            }
        };
    }
]);
