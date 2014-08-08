define ['angular', 'css!style/app/signin'], ->
  'use strict'
  angular.module('controller', [])


  #common controller
  angular.module('controller')
  #AppCtrl is base controller
  .controller 'AppCtrl', ($scope, Local, Alert, Breadcrumb)->
    $scope.time = new Date().getTime()
    #messageNotification.pushForCurrentRoute('errors.route.changeError', 'error',{},{rejection: ''})
    $scope.local = Local
    if (!$.support.leadingWhitespace)
      Alert.addAlert({type: 'danger', msg: "Error - " + Local.get('message', 'errors.browser.ieSupportError')})

    $scope.breadcrumb = Breadcrumb

  #HeaderCtrl is Navbar
  .controller 'HeaderCtrl', ($scope, $log, $modal, AppService, UserService) ->
    isAuth = UserService.isAuthenticated()
    if isAuth
      $scope.menus = UserService.getUser().menus
    else
      $scope.menus = [
        {icon: 'user', name: 'About', url: '/about'}
      ]

    $scope.search = (content)->
      if content && $.trim(content) != ''
        AppService.search(content)

  #FooterCtrl is Version
  .controller 'FooterCtrl', ($scope) ->
    $scope.foot = 'foot'

  #HomeCtrl is first page
  .controller 'HomeCtrl', ($scope, User) ->
    $scope.name = 'baby'

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS!!!',
      'RequireJS',
      'Foundation',
      'Karma',
      'haha']

  #SignupCtrl is sign up page
  .controller 'SignupCtrl', ($scope, Email) ->
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
    $scope.user = {username: '', password: ''}

    $scope.singin = (user, captcha) ->
      UserService.signin(user, captcha)
      $scope.user = user

  .controller 'AboutCtrl', ($scope)->
    $scope.organize = 'Icedog'