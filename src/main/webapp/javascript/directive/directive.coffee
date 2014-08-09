define ['angular'], ->
  'use strict'
  angular.module('directive', [])


  #common directive
  angular.module('directive')
  #test hello tag show and hide
  .directive 'focusMe', ($timeout)->
    link: (scope, element, attrs) ->
      scope.$watch("focusMe", (value)->
        if value == "true"
          $timeout(->
            element[0].focus())
      )

  .directive 'backTop', ->
    restrict: 'EA'
    replace: true #replace tag
    template: '<a class="back-top fa fa-angle-up" href="#"></a>'
    link: (scope, element, attrs) ->
      scope.showBack = false

      $(window).scroll(->
        if ($(window).scrollTop() > 200)
          if (!scope.showBack)
            scope.showBack = true
            #$(element)['fadeIn'](200)
            $(element).fadeIn(200)
        else
          if scope.showBack
            scope.showBack = false
            #$(element)['fadeOut'](200)
            $(element).fadeOut(200)
      )

      $(element).click((e)->
        e.preventDefault()
        $('html, body').animate({scrollTop: '0px'}, 400, 'linear')
      )
  #captcha
  .directive 'captcha', ->
    restrict: 'EA'
    replace: true #replace tag
    template: '<img class="captcha" ng-src="/patchca?width=128&height=45&fontsize=30&time={{time}}" ng-click="reload()" >'

    link: (scope, element, attrs, controller)->
      scope.time = new Date().getTime()
      scope.reload = ->
        scope.time = new Date().getTime()

  .directive 'hello', ->
    restrict: 'EA'
    transclude: true #tag include content reserve
    replace: true #replace hello tag
    scope:
      name: '=helloName'

    template:
      '<div><div ng-click="toggle()">{{name}}</div>' +
      '<div ng-show="showMe" ng-transclude></div>' +
      '</div>'

    link: (scope, element, attrs, controller)->
      scope.loadTime = new Date().getTime()
      scope.showMe = true
      scope.toggle = ->
        scope.showMe = !scope.showMe

