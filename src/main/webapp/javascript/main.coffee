'use strict'

require.config
  baseUrl: '/'
  paths:
    'jQuery': 'webjars/jquery/1.11.1/jquery.min'
    'angular': 'webjars/angularjs/1.3.0-beta.15/angular.min'
    'angular-route': 'webjars/angularjs/1.3.0-beta.15/angular-route.min'
    'firebase': ['https://cdn.firebase.com/v0/firebase', 'webjars/firebase/1.0.17/firebase']
    'angularfire': 'webjars/angularFire/0.7.1/angularfire.min'
    'domReady': 'webjars/requirejs-domready/2.0.1/domReady'
    'app': 'javascript/app'
    'main': 'javascript/main'
    'route': 'javascript/route'
    'controller': 'javascript/controller/controller'
    'directive': 'javascript/directive/directive'
    'filter': 'javascript/filter/filter'
    'model': 'javascript/model/model'
  shim:
    'angular-route': ['angular']
    'angularfire': ['angular', 'firebase']

require ['jQuery', 'route','javascript/controller/home'], ->
  $ ->
    angular.bootstrap document, ['app']
    $('html').addClass('ng-app: app')
