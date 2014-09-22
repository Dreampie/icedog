'use strict'

libsources =
#id like webjars dir
  version:
    'require-css': '0.1.4'
    'jquery': '2.1.1'
    'angularjs': '1.3.0-beta.8'
    'angular-ui-bootstrap': '0.11.0'
    'angular-ui-calendar': '0.8.0'
    'headroomjs': '0.7.0'
    'nprogress': '0.1.2'
    'marked': '0.3.2'
    'highlightjs': '8.0'
    'bootstrap': '3.2.0'
    'font-awesome': '4.1.0'
  cdn:
    'jquery': '//cdn.jsdelivr.net/jquery/'
    'angularjs': '//cdn.bootcss.com/angular.js/'
    'angular-ui-bootstrap': '//cdn.bootcss.com/angular-ui-bootstrap/'
    'angular-ui-calendar': '//cdn.bootcss.com/angular-ui-calendar/'
    'headroomjs': '//cdn.jsdelivr.net/headroomjs/'
    'nprogress': '//cdn.bootcss.com/nprogress/'
    'marked': '//cdn.bootcss.com/marked/'
    'highlightjs': '//cdn.bootcss.com/highlight.js/'
  jarpath: (requireid, webjarsname)->
    'webjars/' + requireid + '/' + libsources.version[requireid] + '/' + webjarsname
#when cdnname  not equal webjarsname  please insert param cdnname
  jarpaths: (requireid, webjarsname, cdnname)->
    webjarspath = libsources.jarpath(requireid, webjarsname)
    if libsources.cdn[requireid]
      #cdnpath = webjars.cdn[requireid] + webjars.version[requireid] + '/' + (cdnname || webjarsname)
      [libsources.cdnpath(requireid, cdnname || webjarsname), webjarspath]
    else
      webjarspath
  localjs: (name)->
    'javascript/' + name
  localcss: (name)->
    'style/' + name
  localpaths: (requireid, localname, cdnname, type = 'js')->
    localpath = if type == 'css' then libsources.localcss(localname) else libsources.localjs(localname)
    if libsources.cdn[requireid]
      #cdnpath = webjars.cdn[requireid] + webjars.version[requireid] + '/' + (cdnname || localname)
      [libsources.cdnpath(requireid, cdnname || localname), localpath]
    else
      localpath
  cdnpath: (requireid, cdnname)->
    libsources.cdn[requireid] + libsources.version[requireid] + '/' + cdnname

requirejs.config
  baseUrl: '/'
  urlArgs: 'v=1.0'
#all of the webjar configs from their webjars-requirejs.js files
  paths:
    'jQuery': libsources.jarpaths('jquery', 'jquery.min')
    'angular': libsources.jarpaths('angularjs', 'angular.min')
    'angular-route': libsources.jarpaths('angularjs', 'angular-route.min')
    'angular-resource': libsources.jarpaths('angularjs', 'angular-resource.min')
    'angular-cookies': libsources.jarpaths('angularjs', 'angular-cookies.min')
    'angular-animate': libsources.jarpaths('angularjs', 'angular-animate.min')
    'angular-ui-bootstrap-tpls': libsources.jarpaths('angular-ui-bootstrap', 'ui-bootstrap-tpls.min')
    'headroom': libsources.localpaths('headroomjs', 'lib/headroom.min', 'headroom.min')
    'angular-headroom': libsources.localpaths('headroomjs', 'lib/angular.headroom.min', 'angular.headroom.min')
    'angular-ui-calendar': libsources.jarpaths('angular-ui-calendar', 'calendar')
    'nprogress': libsources.jarpaths('nprogress', 'nprogress', 'nprogress.min')
    'marked': libsources.jarpaths('marked', 'marked', 'marked.min')
    'angular-marked': libsources.localpaths('angular-marked', 'lib/angular-marked.min', 'angular-marked.min')
    'highlightjs': libsources.jarpaths('highlightjs', 'highlight.min')

    'app': libsources.localjs('app')
    'controller': libsources.localjs('controller/controller')
    'directive': libsources.localjs('directive/directive')
    'filter': libsources.localjs('filter/filter')
    'resource': libsources.localjs('resource/resource')
    'service': libsources.localjs('service/service')
    'local': libsources.localjs('common/local')
  shim:
    'angular': ['jQuery']
    'angular-animate': ['angular']
    'angular-route': ['angular']
    'angular-resource': ['angular']
    'angular-cookies': ['angular']
    'angular-ui-bootstrap-tpls': ['angular']
    'angular-ui-calendar': ['angular']
    'angular-headroom': ['angular', 'headroom']
    'marked':'exports':'marked'
    'angular-marked': ['angular', 'marked']
    'highlightjs': ['css!' + libsources.jarpath('highlightjs', 'styles/default.min')]#webjars/highlightjs/8.0/styles/default.min

    'nprogress': ['css!' + libsources.jarpath('nprogress', 'nprogress')]#webjars/nprogress/0.1.2/nprogress
    'controller': ['css!' + libsources.jarpath('bootstrap', 'css/bootstrap.min'), #webjars/bootstrap/3.2.0/css/bootstrap.min
                   'css!' + libsources.jarpath('font-awesome', 'css/font-awesome.min'), #webjars/font-awesome/4.1.0/css/font-awesome.min
                   'css!' + libsources.localcss('main/layout')]#style/main/layout
  map:
    '*':
      'css': libsources.jarpath('require-css', 'css') #'webjars/require-css/0.1.4/css' #or whatever the path to require-css is

#  waitSeconds: 1

require ['app', 'javascript/controller/schedule'], ->
  $ ->
    NProgress.configure({ showSpinner: false })
    NProgress.start()
    angular.bootstrap document, ['app']
    $('html').attr('ng-app', 'app')
    NProgress.done()
    #require  other modules
    require ['javascript/controller/schedule']
