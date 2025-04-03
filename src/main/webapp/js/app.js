'use strict';

/**
 * Main AngularJS application module
 */
var app = angular.module('legacyApp', ['ngRoute']);

/**
 * Configure routes
 */
app.config(['$routeProvider', '$locationProvider', 
    function($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            })
            .when('/users', {
                templateUrl: 'partials/users.html',
                controller: 'UsersController'
            })
            .when('/products', {
                templateUrl: 'partials/products.html',
                controller: 'ProductsController'
            })
            .otherwise({
                redirectTo: '/'
            });
        
        // Use HTML5 History API if available
        $locationProvider.html5Mode(false);
    }
]);

/**
 * HTTP interceptor for error handling
 */
app.factory('httpInterceptor', ['$q', '$rootScope', 
    function($q, $rootScope) {
        return {
            'responseError': function(rejection) {
                // Handle HTTP errors
                if (rejection.status === 404) {
                    $rootScope.error = 'Resource not found';
                } else if (rejection.status === 500) {
                    $rootScope.error = 'Server error occurred';
                } else {
                    $rootScope.error = 'An error occurred: ' + rejection.statusText;
                }
                return $q.reject(rejection);
            }
        };
    }
]);

/**
 * Configure HTTP interceptors
 */
app.config(['$httpProvider', 
    function($httpProvider) {
        $httpProvider.interceptors.push('httpInterceptor');
    }
]);

/**
 * Main app controller
 */
app.controller('AppController', ['$scope', '$rootScope',
    function($scope, $rootScope) {
        $rootScope.appName = 'Legacy AngularJS Servlet Application';
        
        // Clear error messages when route changes
        $rootScope.$on('$routeChangeStart', function() {
            $rootScope.error = null;
        });
    }
]);
