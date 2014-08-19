define ['controller', 'angular-ui-calendar'], (ctrl)->
  'use strict'
  #common controller
  ctrl.controller 'CalendarCtrl', ($scope)->
    $scope.test = 'hi'
  .controller 'ScheduleCtrl', ($scope)->
    $scope.a = 1