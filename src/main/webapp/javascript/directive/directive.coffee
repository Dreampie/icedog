define ['angular'], ->
  'use strict'
  angular.module('directive', [])


  #common directive
  angular.module('directive')
  #test hello tag show and hide
  .directive 'hello', ->
    restrict: 'EA'
    transclude: true #tag include content reserve
    replace: true #replace hello tag
    scope:
      title: '=helloTitle'

    template:
      '<div><div ng-click="toggle()">{{title}}</div>' +
      '<div ng-show="showMe" ng-transclude></div>' +
      '</div>'

    link: (scope, element, attrs, controller)->
      scope.showMe = true
      scope.toggle = ->
        scope.showMe = !scope.showMe;

