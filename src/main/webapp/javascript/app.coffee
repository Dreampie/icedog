define ['angular', 'angular-route', 'angular-ui-bootstrap-tpls', 'local', 'controller', 'service', 'resource', 'filter',
        'directive'],
->
  'use strict'
  angular.module('app',
    ['ngRoute', 'ui.bootstrap' , 'local', 'controller', 'service', 'resource', 'filter', 'directive'])

  #config app
  angular.module('app').constant 'LOCAL', {
    'resource':
      'image': '/image'
      'javascript': '/javascript'
      'style': '/style'
    'message':
      'errors.route.changeError': 'Route change error'
      'errors.route.401Error': 'Not authorized'
      'errors.route.403Error': 'Not allow access'
      'errors.route.404Error': 'Not fount'
      'errors.route.500Error': 'Server error'
      'errors.route.unknownError': 'Unknown error'
  }
  .config ($routeProvider, $locationProvider, $httpProvider) ->
    #use the HTML5 History API
    $locationProvider.html5Mode(true)

    #$resourceProvider.defaults.stripTrailingSlashes = false
    $httpProvider.defaults.headers.common =
      'x-Requested-With': 'XMLHttpRequest'
    #异常过滤
    $httpProvider.interceptors.push ($q, $location, Local, Alert)->
      responseError: (response) ->
        switch response.status
          when 401 then $location.path('/signin')
          when 403 then Alert.addAlert({type: 'danger', msg: 403 + " - " + Local.get('message', 'errors.route.403Error')})
          when 404 then Alert.addAlert({type: 'danger', msg: 404 + " - " + Local.get('message', 'errors.route.404Error')})
          when 500 then Alert.addAlert({type: 'danger', msg: 500 + " - " + Local.get('message', 'errors.route.500Error')})
          else
            Alert.addAlert({type: 'danger', msg: Local.get('message', 'errors.route.unknownError')})

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
