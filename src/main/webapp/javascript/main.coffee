'use strict'

require.config
  baseUrl: '/'
  paths:
    'jQuery': ['https://code.jquery.com/jquery-1.11.1.min', 'webjars/jquery/1.11.1/jquery.min']
    'angular': 'webjars/angularjs/1.3.0-beta.15/angular.min'
    'angular-route': 'webjars/angularjs/1.3.0-beta.15/angular-route.min'
    'angular-resource': 'webjars/angularjs/1.3.0-beta.15/angular-resource.min'
    'angular-cookies': 'webjars/angularjs/1.3.0-beta.15/angular-cookies.min'
    'angular-animate': 'webjars/angularjs/1.3.0-beta.15/angular-animate.min'
  #'angular-ui-bootstrap': 'webjars/angular-ui-bootstrap/0.11.0/ui-bootstrap.min'
    'angular-ui-bootstrap-tpls': 'webjars/angular-ui-bootstrap/0.11.0/ui-bootstrap-tpls.min'
    'firebase': ['https://cdn.firebase.com/v0/firebase', 'webjars/firebase/1.0.17/firebase']
    'angularfire': 'webjars/angularFire/0.7.1/angularfire.min'
  #'domReady': 'webjars/requirejs-domready/2.0.1/domReady'
    'headroom': ['http://cdn.jsdelivr.net/headroomjs/0.7.0/headroom.min', 'javascript/lib/headroom.min']
    'angular-headroom': ['http://cdn.jsdelivr.net/headroomjs/0.7.0/angular.headroom.min', 'javascript/lib/angular.headroom.min']

    'app': 'javascript/app'
    'controller': 'javascript/controller/controller'
    'directive': 'javascript/directive/directive'
    'filter': 'javascript/filter/filter'
    'resource': 'javascript/resource/resource'
    'service': 'javascript/service/service'
    'local': 'javascript/common/local'

  #gt ie 9
    'es6-shim': 'webjars/es6-shim/0.12.0/es6-shim'
    'json3': 'webjars/json3/3.3.2/json3.min'
    'html5shiv': 'webjars/html5shiv/3.7.2/html5shiv.min'
    'respond': 'webjars/respond/1.4.2/dest/respond.min'
  shim:
    'angular': ['jQuery']
    'angular-animate': ['angular']
    'angular-route': ['angular']
    'angular-resource': ['angular']
    'angular-cookies': ['angular']
  #'angular-ui-bootstrap': ['angular']
    'angular-ui-bootstrap-tpls': ['angular']
    'angularfire': ['angular', 'firebase']

    'angular-headroom': ['angular','headroom']


    'controller': ['css!webjars/bootstrap/3.2.0/css/bootstrap.min', 'css!webjars/font-awesome/4.1.0/css/font-awesome.min',
                   'css!style/main/layout']
  map:
    '*':
      'css': 'webjars/require-css/0.1.4/css' #or whatever the path to require-css is

require ['jQuery', 'app'], ->
  $ ->
    angular.bootstrap document, ['app']
    $('html').addClass('ng-app: app')
