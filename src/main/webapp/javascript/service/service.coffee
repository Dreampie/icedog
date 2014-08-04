define ['angular'], ->
  'use strict'
  angular.module('service', [])


  #common service
  angular.module('service')
  #Breadcrumb  is site map
  .factory 'Breadcrumb', ($rootScope, $location, $log) ->
    data = []

    $rootScope.$on '$routeChangeSuccess', ->
      path = $location.path().trim()

      pathElements = path.split('/') if path != '/'

      breadcrumbPath = (index)->
        '/' + (pathElements.slice 0, index + 1).join '/'


      result = []
      result.push {name: 'home', path: '/'}
      if pathElements
        #delete first element
        pathElements.shift()
        for i in [0..pathElements.length - 1]
          result.push {name: pathElements[i], path: breadcrumbPath(i)}

      data = result


    ->
    all: ->
      #$log.debug data
      data
    first: ->
      data[0] || {}

