define ['angular', 'css!style/app/signin'], ->
  'use strict'
  angular.module('controller', [])

  #AppCtrl is base controller
  .controller 'AppCtrl', ($scope, Message, Alert, BreadcrumbSrv, UserSrv)->
#    $scope.message = Message
    $scope.currentUser = UserSrv.currentUser
    $scope.breadcrumb = BreadcrumbSrv
    $scope.signout = (outpath) ->
      UserSrv.signout((data)->
        $location.path(outpath || '/')
      ,(data)->
        Alert.addAlert({type: 'danger', msg: '退出失败!'})
      )

#    if !$.support.leadingWhitespace
#      Alert.addAlert({type: 'danger', msg: '不支持该浏览器，推荐使用最新版本以获得更好的体验!', keep: true})

  #HeaderCtrl is Navbar
  .controller 'HeaderCtrl', ($scope, $log, $modal, AppSrv, UserSrv) ->
    $scope.menus = [
      {icon: 'user', name: 'Center', url: '/center', children: [{icon: 'user', name: 'Center1', url: '/center'}
                                                                  {icon: 'info', name: 'About1', url: '/about'}]}
      {icon: 'calendar', name: 'Calendar', url: '/calendar'}
      {icon: 'info', name: 'About', url: '/about'}
      {icon: 'pencil-square-o', name: 'Editor', url: '/editor'}
    ]

    #    console.log $scope.menus[0].children.length
    if UserSrv.isAuthed
      $scope.menus = UserSrv.user.menus || $scope.menus

    $scope.searchAll = (content)->
      if content && $.trim(content) != ''
        AppSrv.searchAll(content)


  #FooterCtrl is Version
  .controller 'FooterCtrl', ($scope) ->
    $scope.foot = 'foot'

  #HomeCtrl is first page
  .controller 'HomeCtrl', ($scope, User, UserSrv) ->
    $scope.name = 'baby'

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS!!!',
      'RequireJS',
      'Foundation',
      'Karma',
      'haha']

  #SignupCtrl is sign up page
#  .controller 'SignupCtrl', ($scope, Email) ->
#    $scope.user =
#      username: "example@hello.com"
#      password: "123456"
#
#    $scope.create = (user, captcha) ->
#      $scope.email = new Email()
#      $scope.email.user = user
#      $scope.email.captcha = captcha
#
#      console.log $scope.email


  #SigninCtrl is sign in page
  .controller 'SigninCtrl', ($scope, UserSrv, Alert) ->
    inpath = null
    $scope.signin = (user, captcha) ->
      UserSrv.signin(user, captcha, (data)->
        $location.path(inpath || '/')
      , (data)->
        switch data['shiroLoginFailure']
          when 'UnknownUserException' then Alert.addAlert({type: 'danger', msg: '账户验证失败或已被禁用!'})
          when 'IncorrectCaptchaException'
            console.log("change captcha")
            Alert.addAlert({type: 'danger', msg: '验证码错误!'})
          else
            Alert.addAlert({type: 'danger', msg: '账户验证失败或已被禁用!'})
      )

  .controller 'EditorCtrl', ($scope)->
    $scope.md = '*This* **is** [markdown](https://daringfireball.net/projects/markdown/)'
  #About me
  .controller 'AboutCtrl', ($scope)->
    $scope.organize = 'Icedog'