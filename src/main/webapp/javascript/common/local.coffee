define ['angular'], ->
  'use strict'
  angular.module('local', [])


  #common service
  angular.module('local').factory 'local', ['$interpolate', 'LOCAL', ($interpolate, local) ->
    notFound = (msg, msgKey)->
      msg || '?' + msgKey + '?'

    get: (msgType, msgKey, interpolateParams)->
      msg = local[msgType][msgKey]

      if (msg)
        $interpolate(msg)(interpolateParams)
      else
        notFound(msg, msgKey)
  ]