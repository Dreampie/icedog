define ['controller', 'javascript/service/email'], ->
  'use strict'
  angular.module('controllers').controller 'Controller', ($scope) ->
    $scope.name = 'baby'

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS!!!',
      'RequireJS',
      'Foundation',
      'Karma']

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
