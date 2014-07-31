define ['angular', 'angular-route', 'local', 'notification', 'controller', 'service', 'resource', 'filter', 'directive'],
->
  'use strict'
  angular.module('app', ['ngRoute', 'local', 'notification', 'controller', 'service', 'resource', 'filter', 'directive'])

  #config app
  angular.module('app').constant 'LOCAL', {
    'resource':
      'image': ''
      'javascript': ''
      'style': ''
    'message':
      'errors.route.changeError': 'Route change error'
  }
  .config ($routeProvider, $locationProvider) ->
    #use the HTML5 History API
    $locationProvider.html5Mode(true)

    $routeProvider
    .when '/',
      templateUrl: 'view/app/home.html', controller: 'HomeCtrl'
    .when '/signup',
      templateUrl: 'view/app/signup.html', controller: 'SignupCtrl'
    .when '/signin',
      templateUrl: 'view/app/signin.html', controller: 'SigninCtrl'
    .otherwise
        redirectTo: '/'