define ['angular', 'angular-resource'], ->
  'use strict'
  angular.module('resource', ['ngResource'])


  #common service
  angular.module('resource').factory 'Email', ($resource) ->
    email = $resource('/signupEmail')

  .factory 'User', ($resource)->
    user: $resource('/user/:id', {id: '@id'}),
    users: $resource('/user')
