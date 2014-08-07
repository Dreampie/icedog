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

  .factory 'Alert', ($rootScope, $timeout) ->
    $rootScope.alerts = []
    $rootScope.timers = []
    addAlert: (message)->
      alert =
        type: message.type
        msg: message.msg
        close: (alert)->
          index = $rootScope.alerts.indexOf(alert)
          $timeout.cancel($rootScope.timers[index])
          $rootScope.alerts.splice(index, 1)

      $rootScope.alerts.push(alert)
      index = $rootScope.alerts.indexOf(alert)

      $rootScope.timers[index] = $timeout ->
        $rootScope.$apply(->
          index = $rootScope.alerts.indexOf(alert)
          $rootScope.alerts.splice(index, 1)
        )
      , 5000


    closeAlert: (index) ->
      $timeout.cancel($rootScope.timers[index])
      $rootScope.alerts.splice(index, 1)
