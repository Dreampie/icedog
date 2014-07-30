define ['controller','javascript/directive/common-directive', 'javascript/service/common-service','javascript/filter/common-filter'], ->
  'use strict'
  angular.module('controller').controller 'Controller', ($scope) ->
    $scope.name = 'baby'

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS!!!',
      'RequireJS',
      'Foundation',
      'Karma',
      'haha']

  .controller 'SignupController', ($scope, EmailService) ->
    $scope.time = new Date().getTime()

    $scope.user =
      email: "example@hello.com"
      password: "123456"

    $scope.create = (user, captcha) ->
      $scope.emailService = new EmailService()
      $scope.emailService.user = user
      $scope.emailService.captcha = captcha

      console.log $scope.emailService

      $scope.emailService.$save()

  .controller 'SigninController', ($scope) ->
    $scope.post = (user) ->
      console.log user
