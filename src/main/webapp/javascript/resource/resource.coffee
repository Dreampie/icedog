define ['angular', 'angular-resource'], ->
  'use strict'
  angular.module('resource', ['ngResource'])


  #common service
  angular.module('resource')
  #user model
  .factory 'User', ($http)->
    signin: (user, captcha, success, error)->
      $http.get('/signin', {user: user, captcha: captcha}).success(success).error(error)
    signout: (success, error)->
      $http.get('/signout').success(success).error(error)
    signup: (user, success, error)->
      $http.get('/signup', {user: user}).success(success).error(error)
    get: (user, success, error)->
      $http.get('/user/get/' + user.id).success(success).error(error)
    save: (user, success, error)->
      $http.post('/user/save', {user: user}).success(success).error(error)
    delete: (user, success, error)->
      $http.delete('/user/delete/' + user.id).success(success).error(error)
    update: (user, success, error)->
      $http.post('/user/update', {user: user}).success(success).error(error)
    query: (user, success, error)->
      $http.get('/user/query', {user: user}).success(success).error(error)
  #email model
  .factory 'Email', ($http) ->
    signup: (email, success, error)->
      $http.get('/signupEmail', {email: email}).success(success).error(error)
