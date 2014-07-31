define ['angular', 'directive', 'resource', 'filter'], ->
  'use strict'
  angular.module('controller', [])


  #common controller
  angular.module('controller').controller 'HeaderCtrl', ($scope, breadcrumb) ->
    $scope.breadcrumb = breadcrumb

  .controller 'FooterCtrl', ($scope) ->
    $scope.foot = 'foot'

  .controller 'HomeCtrl', ($scope) ->
    $scope.name = 'baby'

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS!!!',
      'RequireJS',
      'Foundation',
      'Karma',
      'haha']

  .controller 'SignupCtrl', ($scope, Email) ->
    $scope.time = new Date().getTime()

    $scope.user =
      email: "example@hello.com"
      password: "123456"

    $scope.create = (user, captcha) ->
      $scope.email = new Email()
      $scope.email.user = user
      $scope.email.captcha = captcha

      console.log $scope.email

      $scope.email.$save()

  .controller 'SigninCtrl', ($scope) ->
    $scope.post = (user) ->
      console.log user