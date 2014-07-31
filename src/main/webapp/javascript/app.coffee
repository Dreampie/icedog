define ['angular', 'angular-route', 'controller', 'service', 'resource', 'filter', 'directive'],
->
  'use strict'
  angular.module('app', ['ngRoute', 'controller', 'service', 'resource', 'filter', 'directive'])

  #config app
  angular.module('app').config ($routeProvider) ->
    $routeProvider
    .when '/',
      templateUrl: 'view/app/home.html', controller: 'HomeCtrl'
    .when '/signup',
      templateUrl: 'view/app/signup.html', controller: 'SignupCtrl'
    .when '/signin',
      templateUrl: 'view/app/signin.html', controller: 'SigninCtrl'
    .otherwise
        redirectTo: '/'
