define ['angular'], ->
  'use strict'
  angular.module('service', [])


  #common service
  angular.module('service').factory 'breadcrumb', ($rootScope, $location, $log) ->
    breadcrumb = {}
    data = []

    $rootScope.$on '$routeChangeSuccess', ->
      pathElements = $location.path().split '/'

      breadcrumbPath = (index)->
        '/' + (pathElements.slice 0, index + 1).join '/'

      pathElements.shift()

      result = []
      for i in [0..pathElements.length - 1]
        result.push {name: pathElements[i], path: breadcrumbPath(i)}

      data = result

    breadcrumb.all = ->
      $log.debug data
      data

    breadcrumb.first = ->
      data[0] || {}

    breadcrumb
