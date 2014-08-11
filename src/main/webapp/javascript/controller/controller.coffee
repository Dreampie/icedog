define ['angular', 'css!style/app/signin'], ->
  'use strict'
  angular.module('controller', [])


  #common controller
  angular.module('controller')
  #AppCtrl is base controller
  .controller 'AppCtrl', ($scope, Local, Alert, Breadcrumb, UserService)->
    #messageNotification.pushForCurrentRoute('errors.route.changeError', 'error',{},{rejection: ''})
    $scope.local = Local

    $scope.user = UserService.user

    if (!$.support.leadingWhitespace)
      Alert.addAlert({type: 'danger', msg: "Error - " + Local.get('message', 'errors.browser.ieSupportError')})

    $scope.breadcrumb = Breadcrumb

    $scope.signout = (outpath,isReload) ->
      UserService.signout(outpath,isReload)

  #HeaderCtrl is Navbar
  .controller 'HeaderCtrl', ($scope, $log, $modal, AppService, UserService) ->
    $scope.menus = [
      {icon: 'user', name: 'About', url: '/about'}
    ]

    if UserService.isAuthed
      $scope.menus = UserService.user.menus || $scope.menus

    $scope.showSearchFocus = false
    $scope.searchAll = (content)->
      if content && $.trim(content) != ''
        AppService.searchAll(content)


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
      username: "example@hello.com"
      password: "123456"

    $scope.create = (user, captcha) ->
      $scope.email = new Email()
      $scope.email.user = user
      $scope.email.captcha = captcha

      console.log $scope.email


  #SigninCtrl is sign in page
  .controller 'SigninCtrl', ($scope, UserService) ->
    $scope.signin = (user, captcha) ->
      UserService.signin(user, captcha)
  #About me
  .controller 'AboutCtrl', ($scope)->
    $scope.organize = 'Icedog'