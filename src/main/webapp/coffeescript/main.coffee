'use strict'

require.config
  baseUrl: '/'
  urlArgs: 'v=1.0'
  paths:
    'jQuery': ['//code.jquery.com/jquery-2.1.1.min', 'webjars/jquery/2.1.1/jquery.min']
    'angular': ['//cdn.bootcss.com/angular.js/1.3.0-beta.8/angular.min','webjars/angularjs/1.3.0-beta.17/angular.min']
    'angular-route': ['//cdn.bootcss.com/angular.js/1.3.0-beta.8/angular-route.min','webjars/angularjs/1.3.0-beta.17/angular-route.min']
    'angular-resource': ['//cdn.bootcss.com/angular.js/1.3.0-beta.8/angular-resource.min','webjars/angularjs/1.3.0-beta.17/angular-resource.min']
    'angular-cookies': ['//cdn.bootcss.com/angular.js/1.3.0-beta.8/angular-cookies.min','webjars/angularjs/1.3.0-beta.17/angular-cookies.min']
    'angular-animate': ['//cdn.bootcss.com/angular.js/1.3.0-beta.8/angular-animate.min','webjars/angularjs/1.3.0-beta.17/angular-animate.min']
  #'angular-ui-bootstrap': 'webjars/angular-ui-bootstrap/0.11.0/ui-bootstrap.min'
    'angular-ui-bootstrap-tpls': ['//cdn.bootcss.com/angular-ui-bootstrap/0.11.0/ui-bootstrap-tpls.min','webjars/angular-ui-bootstrap/0.11.0/ui-bootstrap-tpls.min']
  #'domReady': 'webjars/requirejs-domready/2.0.1/domReady'
    'headroom': ['//cdn.jsdelivr.net/headroomjs/0.7.0/headroom.min', 'javascript/lib/headroom.min']
    'angular-headroom': ['//cdn.jsdelivr.net/headroomjs/0.7.0/angular.headroom.min','javascript/lib/angular.headroom.min']
    'angular-ui-calendar':['//cdn.bootcss.com/angular-ui-calendar/0.8.0/calendar.min','webjars/angular-ui-calendar/0.9.0-beta.1/calendar']
    'app': 'javascript/app'
    'route':'javascript/route'
    'controller': 'javascript/controller/controller'
    'directive': 'javascript/directive/directive'
    'filter': 'javascript/filter/filter'
    'resource': 'javascript/resource/resource'
    'service': 'javascript/service/service'
    'local': 'javascript/common/local'
  shim:
    'angular': ['jQuery']
    'angular-animate': ['angular']
    'angular-route': ['angular']
    'angular-resource': ['angular']
    'angular-cookies': ['angular']
  #'angular-ui-bootstrap': ['angular']
    'angular-ui-bootstrap-tpls': ['angular']
    'angular-ui-calendar': ['angular']
    'angular-headroom': ['angular', 'headroom']


    'controller': ['css!webjars/bootstrap/3.2.0/css/bootstrap.min',
                   'css!webjars/font-awesome/4.1.0/css/font-awesome.min',
                   'css!style/main/layout']
  map:
    '*':
      'css': 'webjars/require-css/0.1.4/css' #or whatever the path to require-css is

require ['app','javascript/controller/schedule'], ->
  $ ->
    angular.bootstrap document, ['app']
    $('html').attr('ng-app','app')
