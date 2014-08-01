define ['angular'], ->
  'use strict'
  angular.module('controller', [])


  #common controller
  #  controller 'AppCtrl',['$scope','LOCAL',($scope,local)->
  #    $scope.errorMsg=local.message['errors.route.changeError']
  angular.module('controller').controller 'AppCtrl', ($scope, local, messageNotification)->
#    messageNotification.pushForCurrentRoute('errors.route.changeError', 'error',{},{rejection: ''})
    $scope.local = local
    console.log $scope.local.get('resource','javascript')

    $scope.notification = messageNotification
    #    console.log $scope.notification

    $scope.removeNotification = (notification) ->
      messageNotification.remove(notification)

    $scope.$on '$routeChangeError', (event, current, previous, rejection)->
      messageNotification.pushForCurrentRoute('errors.route.changeError', 'error', {}, {rejection: rejection})

  .controller 'HeaderCtrl', ($scope, breadcrumb) ->
    $scope.breadcrumb = breadcrumb

  .controller 'FooterCtrl', ($scope) ->
    $scope.foot = 'foot'

  .controller 'HomeCtrl', ($scope, User) ->
    $scope.name = 'baby'

    user = User.user.get({id: 1},
    (response)->
      console.log response
    ,
    (error)->
      console.log error)

#    console.log(user)

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