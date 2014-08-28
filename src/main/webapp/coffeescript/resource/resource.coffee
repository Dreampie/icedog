define ['angular', 'angular-resource'], ->
  'use strict'
  angular.module('resource', ['ngResource'])

  .factory 'Area', ($http)->
    query: (area = {pid: -1, isdelete: false, istree: false}, success, error)->
      $http.get('/area/query', {pid: area.pid, isdelete: area.isdelete, istree: area.istree}).success(success).error(error)
  #user model
  .factory 'User', ($http)->
    signin: (user, captcha, success, error)->
      $http.post('/signin',
        {username: user.username, password: user.password, captcha: captcha}).success(success).error(error)
    signout: (success, error)->
      $http.get('/signout').success(success).error(error)
#    signup: (user, success, error)->
#      $http.post('/signup', {user: user}).success(success).error(error)
    get: (user, success, error)->
      $http.get('/user/get/' + user.id).success(success).error(error)
    save: (user, success, error)->
      $http.post('/user/save', {user: user}).success(success).error(error)
    delete: (user, success, error)->
      $http.get('/user/delete/' + user.id).success(success).error(error)
    update: (user, success, error)->
      $http.post('/user/update', {user: user}).success(success).error(error)
    query: (user, success, error)->
      $http.get('/user/query', {user: user}).success(success).error(error)
  #email model
  .factory 'Email', ($http) ->
    signup: (email, success, error)->
      $http.get('/signupEmail', {email: email}).success(success).error(error)
