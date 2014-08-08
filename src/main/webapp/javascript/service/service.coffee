define ['angular'], ->
  'use strict'
  angular.module('service', [])


  #common service
  angular.module('service')
  #Breadcrumb  is site map
  .factory 'Breadcrumb', ($rootScope, $location) ->
    data = []

    $rootScope.$on '$routeChangeSuccess', ->
      path = $location.path().trim()

      pathElements = path.split('/') if path != '/'

      breadcrumbPath = (index)->
        '/' + (pathElements.slice 0, index + 1).join '/'


      result = []
      result.push {name: 'home', path: '/'}
      if pathElements
        #delete first element
        pathElements.shift()
        for i in [0..pathElements.length - 1]
          result.push {name: pathElements[i], path: breadcrumbPath(i)}

      data = result

    all: ->
      #$log.debug data
      data
    first: ->
      data[0] || {}

  .factory 'AppService', ->
    search: (content)->
      console.log content

  .factory 'UserService', ($cookieStore, User, Alert)->
    currentUser = $cookieStore.get('user') || { full_name: '访客'}

    getUser: ->
      currentUser

    isAuthenticated: ->
      if currentUser.username
        true
      else false

    signin: (user, captcha)->
      User.signin(user, captcha
        (data)->
          $cookieStore.put('user', data.user)
      , (data)->
        switch data.shiroLoginFailure
          when 'UnknownUserException' then Alert.addAlert({type: 'danger', msg: '账户验证失败或已被禁用!'})
          when 'IncorrectCaptchaException' then Alert.addAlert({type: 'danger', msg: '验证码错误!'})
          else
            Alert.addAlert({type: 'danger', msg: '账户验证失败或已被禁用!'})
      )

    signup: (user)->
      User.signup(
        user, (data)->

      , (data)->
      )

    signout: ->
      User.signout(
        (data)->

      , (data)->
      )