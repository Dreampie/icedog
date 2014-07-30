define ['directive'], ->
  'use strict'
  angular.module('directive', []).directive 'hello', ->
    restrict: 'EA'
    transclude: true #tag include content reserve
    replace: true #replace hello tag
    scope:
      name: '=helloName'

    template:
      '<div><div ng-click="toggle()">{{name}}</div>' +
      '<div ng-show="showMe" ng-transclude></div>' +
      '</div>'

    link: (scope, element, attrs)->
      scope.showMe = true
      scope.toggle = ->
        scope.showMe = !scope.showMe;

