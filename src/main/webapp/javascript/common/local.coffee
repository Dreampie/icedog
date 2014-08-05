define ['angular'], ->
  'use strict'
  angular.module('local', [])


  #common service
  angular.module('local').factory 'Local', ['$interpolate', 'LOCAL', ($interpolate, local) ->
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

  .factory 'Alert', ($rootScope) ->
    $rootScope.alerts = []

    addAlert: (message)->
      $rootScope.alerts.push(
        type: message.type
        msg: message.msg
        close: (index)->
          $rootScope.alerts.splice(index, 1)
      )

    closeAlert: (index) ->
      $rootScope.alerts.splice(index, 1)