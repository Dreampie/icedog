define ['angular'], ->
  'use strict'
  angular.module('service', [])


  #common service
  angular.module('service')
  #Breadcrumb  is site map
  .factory 'Breadcrumb', ($rootScope, $location, $log) ->
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
    searchAll: (content)->
      console.log content

  .factory 'UserService', ($cookieStore, $location, User, Alert)->
    guestUser = { full_name: '访客', isAuthed: false}
    currentUser = $cookieStore.get('current_user') || guestUser
    #    isAuthenticated = !!(currentUser.id)

    authUtils =
      changeUser: (user)->
        if user
#          isAuthenticated=true
          user.isAuthed = true
          $cookieStore.put('current_user', user)
          angular.extend(currentUser || {}, user)
      removeUser: ->
#        isAuthenticated=false
        $cookieStore.remove('current_user')
        angular.extend(currentUser || {}, guestUser)


    signin: (user, captcha)->
      User.signin(user, captcha,
      (data)->
        if data.user
          authUtils.changeUser(data.user)
          $location.path('/')
        else
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

    signout: (outpath)->
      User.signout(
        (data)->
          authUtils.removeUser()
          $location.path(outpath || '/')
      , (data)->
      )

    user: currentUser
#    isAuthed:isAuthenticated