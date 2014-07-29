

define ['controller'], ->
  'use strict'
  angular.module('controllers').controller 'Controller', ($scope) ->
    $scope.name = 'baby'

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS!!!',
      'RequireJS',
      'Foundation',
      'Karma']

  .controller 'SignupController', ($scope) ->
    $scope.create = (user) ->
      console.log user

    $scope.user =
      email: "example@hello.com"
      password: "123456"

  .controller 'SigninController', ($scope) ->
    $scope.post = (user) ->
      console.log user