define ['angular'], ->
  'use strict'
  angular.module('local', [])


  #common service
  angular.module('local').factory 'local', ['$interpolate', 'LOCAL', ($interpolate, local) ->
    notFound = (msg, msgType, msgKey)->
      msg || '?' + msgType + ':' + msgKey + '?'


    get: (msgType, msgKey, interpolateParams)->
      if(msgKey)
        msg = local[msgType][msgKey]
      else
        msg = local[msgType]

      if (msg)
        $interpolate(msg)(interpolateParams)
      else
        notFound(msg, msgType, msgKey)
  ]