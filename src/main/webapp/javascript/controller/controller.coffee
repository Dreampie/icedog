define ['angular'], ->
  'use strict'
  angular.module('controller', [])


  #common controller
  angular.module('controller')
  #AppCtrl is base controller
  .controller 'AppCtrl', ($scope, Local, Alert, $http)->
    #messageNotification.pushForCurrentRoute('errors.route.changeError', 'error',{},{rejection: ''})
    $scope.local = Local
    if (!$.support.leadingWhitespace)
      Alert.addAlert({type: 'danger', msg: "Error - " + Local.get('message', 'errors.browser.ieSupportError')})

#    $http.get('/').success((data)->console.log data.user).error((data)->console.log data)
  #left menu
  .controller 'LefterCtrl', ($scope, $rootScope)->
    $scope.menus = $rootScope.user.menus

  #HeaderCtrl is Navbar
  .controller 'HeaderCtrl', ($scope, $log, $modal, Breadcrumb) ->
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


  #SigninCtrl is sign in page
  .controller 'SigninCtrl', ($scope, UserService) ->
    $scope.post = (user) ->
      UserService.signin(user)