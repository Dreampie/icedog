'use strict'

require.config
  baseUrl: '/javascript/'
  paths:
    'jQuery': ['https://code.jquery.com/jquery-1.11.1.min', '../webjars/jquery/1.11.1/jquery.min']
    'angular': '../webjars/angularjs/1.3.0-beta.15/angular.min'
    'angular-route': '../webjars/angularjs/1.3.0-beta.15/angular-route.min'
    'angular-resource': '../webjars/angularjs/1.3.0-beta.15/angular-resource.min'
    'angular-cookie': '../webjars/angularjs/1.3.0-beta.15/angular-cookies.min'
  #'angular-ui-bootstrap': '../webjars/angular-ui-bootstrap/0.11.0/ui-bootstrap.min'
    'angular-ui-bootstrap-tpls': '../webjars/angular-ui-bootstrap/0.11.0/ui-bootstrap-tpls.min'
    'firebase': ['https://cdn.firebase.com/v0/firebase', '../webjars/firebase/1.0.17/firebase']
    'angularfire': '../webjars/angularFire/0.7.1/angularfire.min'
  #'domReady': '../webjars/requirejs-domready/2.0.1/domReady'
    'controller': 'controller/controller'
    'directive': 'directive/directive'
    'filter': 'filter/filter'
    'resource': 'resource/resource'
    'service': 'service/service'
    'local': 'common/local'
  shim:
    'angular': ['jQuery']
    'angular-route': ['angular']
    'angular-resource': ['angular']
    'angular-cookie': ['angular']
  #'angular-ui-bootstrap': ['angular']
    'angular-ui-bootstrap-tpls': ['angular']
    'angularfire': ['angular', 'firebase']
    'controller': ['css!../webjars/bootstrap/3.2.0/css/bootstrap.min']
  map:
    '*':
      'css': '../webjars/require-css/0.1.4/css' #or whatever the path to require-css is

require ['jQuery', 'app'], ->
  $ ->
    angular.bootstrap document, ['app']
    $('html').addClass('ng-app: app')
