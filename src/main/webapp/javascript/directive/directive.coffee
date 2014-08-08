define ['angular'], ->
  'use strict'
  angular.module('directive', [])


  #common directive
  angular.module('directive')
  #test hello tag show and hide
  .directive 'autoFocus', ($timeout)->
    link: (scope, element, attrs) ->
      attrs.$observe("autoFocus", (newValue)->
        if newValue == "true"
          $timeout(->
            element[0].focus())
      )

  .directive 'backTop', ->
    restrict: 'EA'
    replace: true #replace hello tag
    template:
      '<a class="back-top fa fa-angle-up" href="#" ng-show="showBack"></a>'
    link: (scope, element, attrs) ->
      scope.showBack = false

      $(window).scroll(->
        scope.$apply(->
          if ($(window).scrollTop() > 100)
            scope.showBack = true
          else
            scope.showBack = false
        )
      )

      $(element).click(->
        $('html, body').animate({scrollTop: '0px'}, 400, 'linear')
        scope.showBack = false
      )

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

