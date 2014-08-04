define ['angular'], ->
  'use strict'
  angular.module('controller', [])


  #common controller
  angular.module('controller')
  #AppCtrl is base controller
  .controller 'AppCtrl', ($scope, local)->
    #messageNotification.pushForCurrentRoute('errors.route.changeError', 'error',{},{rejection: ''})
    $scope.local = local
    #console.log $scope.local.get('resource', 'javascript')
    $scope.alerts = [
      {type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.'},
      {type: 'success', msg: 'Well done! You successfully read this important alert message.'}
    ]
    $scope.showAlert = $scope.alerts.length > 0

    $scope.addAlert = (message)->
      $scope.alerts.push({type: message.type, msg: message.msg})

    $scope.closeAlert = (index) ->
      $scope.alerts.splice(index, 1)


  #show error
  .controller 'ErrorCtrl', ($scope, $location, local) ->
    $scope.errorCode = $location.search()['code']
    $scope.errorCode ? 404

    $scope.errorMsg = $scope.errorCode + " - " + local.get('message', 'errors.route.' + $scope.errorCode + 'Error')

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
    $scope.addAlert({type:'info',msg:'test'})

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