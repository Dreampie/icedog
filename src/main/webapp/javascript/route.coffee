'use strict'
define ['app', 'controller'], ->
  angular.module('app').config ($routeProvider) ->
    $routeProvider
    .when '/',
      templateUrl: 'view/app/home.html', controller: 'Controller'
    .when '/signup',
      templateUrl: 'view/app/signup.html', controller: 'SignupController'
    .when '/signin',
      templateUrl: 'view/app/signin.html', controller: 'SigninController'
    .otherwise
        redirectTo: '/'
