'use strict'

require.config
  baseUrl: '/'
  paths:
    'jQuery': 'webjars/jquery/1.11.1/jquery.min'
    'angular': 'webjars/angularjs/1.3.0-beta.15/angular.min'
    'angular-route': 'webjars/angularjs/1.3.0-beta.15/angular-route.min'
    'angular-resource': 'webjars/angularjs/1.3.0-beta.15/angular-resource.min'
    'angular-cookie': 'webjars/angularjs/1.3.0-beta.15/angular-cookies.min'
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
    'service': 'javascript/service/service'
  shim:
    'angular-route': ['angular']
    'angular-resource': ['angular']
    'angular-cookie': ['angular']
    'angularfire': ['angular', 'firebase']


require ['jQuery', 'app'], ->
  $ ->
    angular.bootstrap document, ['app']
    $('html').addClass('ng-app: app')
