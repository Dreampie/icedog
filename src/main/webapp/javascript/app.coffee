define ['angular', 'angular-route', 'controller', 'service', 'filter', 'directive'],
->
  'use strict'
  angular.module('app', ['ngRoute', 'controller', 'service', 'filter', 'directive'])

#config app
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
