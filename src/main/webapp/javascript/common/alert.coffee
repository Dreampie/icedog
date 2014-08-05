define ['angular'], ->
  'use strict'
  angular.module('alert', [])

  angular.module('alert').factory 'Alert', (Local)->
    ->
      alert.alerts = []
      Alert.showAlert = $scope.alerts.length > 0

      Alert.addAlert = (message)->
        Alert.alerts.push({type: message.type, msg: message.msg})

      Alert.closeAlert = (index) ->
        Alert.alerts.splice(index, 1)