define ['angular'], ->
  'use strict'
  angular.module('service', [])

  #Breadcrumb  is site map
  .factory 'BreadcrumbSrv', ($rootScope, $location, $log) ->
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

  .factory 'AppSrv', ->
    searchAll: (content)->
      console.log content

  .factory 'WebSocketSrv', ($timeout, $location, $log) ->
    connect: (url)->
      if !window.WebSocket
        $log.error 'cannot open websocket'
        return
      wsClient = new WebSocket(url)

      wsClient.onopen = (event) ->
        wsClient.readySate = true

      wsClient.onmessage = (event)->
        msg = JSON.parse(event.data)
        $log.info(msg)

      wsClient.onclose = (event) ->
        wsClient.readySate = false
        $log.info("close websocket")

      wsClient.onerror = (event)->
        wsClient.readySate = false
        $log.error(event.data)
      wsClient

  .factory 'AreaSrv', ($cookieStore, Area) ->
    allAreas = $cookieStore.get('allAreas') || {isLoad: false}

    areaUtils =
      changeArea: (areas)->
        if areas
          areas.isLoad = true
          $cookieStore.put('allAreas', areas)
          angular.extend(allAreas || {}, areas)
    #      getArea:(id...)->
    #        if !allAreas.isLoad

    loadArea: (area)->
      Area.query(area,
      (data)->
        if(data.areas)
          areaUtils.changeArea data.areas
      )
    removeArea: ->
      $cookieStore.remove('allAreas')
      angular.extend(allAreas || {}, {isLoad: false})

  .factory 'UserSrv', ($q,$cookieStore, $location, $window, User, Alert, WebSocketSrv)->

    #获取当前登录的用户
    currentUser = { full_name: '访客', isAuthed: false}

    authUtils =
      changeUser: (user)->
        if user
          user.isAuthed = true
          $cookieStore.put('current_user', user)
          angular.extend(currentUser || {}, user)
          #登录成功  连接websocket
          WebSocketSrv.connect("ws://localhost:9090/im/" + user.id)
      removeUser: ->
        $cookieStore.remove('current_user')
        angular.extend(currentUser || {}, { full_name: '访客', isAuthed: false})
      isAuthed: ->
        defer = $q.defer()
        User.authed(
          (data)->
            defer.resolve(data.isAuthed)
        , ->
          defer.reject(false)
        )
        defer.promise
    isAuthed=authUtils.isAuthed()
    isAuthed.then(
      (data)->
        if data
          authUtils.changeUser($cookieStore.get('current_user'))
    )

    signin: (user, captcha, outpath, isReload)->
      User.signin(user, captcha,
      (data)->
        if(data['authc.FILTERED'] && data.user)
          authUtils.changeUser(data.user)
          #          console.log currentUser
          if(isReload)
            $window.location.href = outpath || '/'
          else
            $location.path(outpath || '/')
        else
          switch data['shiroLoginFailure']
            when 'UnknownUserException' then Alert.addAlert({type: 'danger', msg: '账户验证失败或已被禁用!'})
            when 'IncorrectCaptchaException' then Alert.addAlert({type: 'danger', msg: '验证码错误!'})
            else
              Alert.addAlert({type: 'danger', msg: '账户验证失败或已被禁用!'})
      )

    signout: (outpath, isReload)->
      User.signout(
        (data)->
          if(data['signout.FILTERED'])
            authUtils.removeUser()

            #  console.log currentUser
            if(isReload)
              $window.location.href = outpath || '/'
            else
              $location.path(outpath || '/')
          else
            Alert.addAlert({type: 'danger', msg: '退出失败!'})
      )

#    signup: (user)->
#      User.signup(
#        user, (data)->
#      )
    currentUser: currentUser