define ['angular', 'angular-resource'], ->
  'use strict'
  angular.module('resource', ['ngResource'])


  #common service
  angular.module('resource').factory 'Email', ($resource) ->
    $resource '/signupEmail'