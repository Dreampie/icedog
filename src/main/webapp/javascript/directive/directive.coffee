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
    template: '<a class="back-top fa fa-angle-up" href="#"></a>'
    link: (scope, element, attrs) ->
      attrs.$observe("backTop", ->
        showBack = false

        $(window).scroll(->
          if ($(window).scrollTop() > 100)
            if (!showBack)
              showBack = true
              #$(element)['fadeIn'](200)
              $(element).fadeIn(200)
          else
            if showBack
              showBack = false
              #$(element)['fadeOut'](200)
              $(element).fadeOut(200)
        )

        $(element).click((e)->
          e.preventDefault()
          $('html, body').animate({scrollTop: '0px'}, 400, 'linear')
        )
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

