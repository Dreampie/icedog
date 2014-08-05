define ['angular'], ->
  'use strict'
  angular.module('controller', [])


  #common controller
  angular.module('controller')
  #AppCtrl is base controller
  .controller 'AppCtrl', ($scope, Local, Alert)->
    #messageNotification.pushForCurrentRoute('errors.route.changeError', 'error',{},{rejection: ''})
    $scope.local = Local
    #console.log $scope.local.get('resource', 'javascript')

  #show error
  .controller 'ErrorCtrl', ($scope, $location, Local) ->
    $scope.errorCode = $location.search()['code']
    $scope.errorCode ? 404

    $scope.errorMsg = $scope.errorCode + " - " + Local.get('message', 'errors.route.' + $scope.errorCode + 'Error')

  #HeaderCtrl is Navbar
  .controller 'HeaderCtrl', ($scope, Breadcrumb) ->
    $scope.breadcrumb = Breadcrumb

  #FooterCtrl is Version
  .controller 'FooterCtrl', ($scope) ->
    $scope.foot = 'foot'

  #HomeCtrl is first page
  .controller 'HomeCtrl', ($scope, User) ->
    $scope.name = 'baby'

    user = User.get({id: 1},
    (response)->
      console.log response
    ,
    (error)->
      console.log error)

    #console.log(user)

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS!!!',
      'RequireJS',
      'Foundation',
      'Karma',
      'haha']

  #SignupCtrl is sign up page
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

  #SigninCtrl is sign in page
  .controller 'SigninCtrl', ($scope) ->
    $scope.post = (user) ->
      console.log user