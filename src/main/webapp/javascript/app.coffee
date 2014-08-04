define ['angular', 'angular-route', 'local', 'notification', 'controller', 'service', 'resource', 'filter',
        'directive'],
->
  'use strict'
  angular.module('app',
    ['ngRoute', 'local', 'notification', 'controller', 'service', 'resource', 'filter', 'directive'])

  #config app
  angular.module('app').constant 'LOCAL', {
    'resource':
      'image': '/image'
      'javascript': '/javascript'
      'style': '/style'
    'message':
      'errors.route.changeError': 'Route change error'
      'errors.route.401Error': 'Your account not authorized'
      'errors.route.403Error': 'Your cant allow access'
      'errors.route.404Error': 'Your page not fount'
      'errors.route.500Error': 'Server error'
  }
  .config ($routeProvider, $locationProvider, $httpProvider) ->
    #use the HTML5 History API
    $locationProvider.html5Mode(true)

    #$resourceProvider.defaults.stripTrailingSlashes = false
    $httpProvider.defaults.headers.common =
      'x-Requested-With': 'XMLHttpRequest'
    #异常过滤
    $httpProvider.interceptors.push ($q, $location)->
      'responseError': (response) ->
        switch response.status
          when '401' then $location.path('/signin')
#          when '403' then $location.path('/error').search({code: 403})
#          when '404' then $location.path('/error').search({code: 404})
#          when '500' then $location.path('/error').search({code: 500})
#          else  $location.path('/error').search({code: 404})

        $q.reject(response)

    $routeProvider
    .when '/',
      templateUrl: 'view/app/home.html', controller: 'HomeCtrl'
    .when '/error',
      templateUrl: 'view/app/error.html', controller: 'ErrorCtrl'
    .when '/signup',
      templateUrl: 'view/app/signup.html', controller: 'SignupCtrl'
    .when '/signin',
      templateUrl: 'view/app/signin.html', controller: 'SigninCtrl'
    .otherwise
      redirectTo: '/error'
