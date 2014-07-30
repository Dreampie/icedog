define ['angular', 'angular-resource'], ->
  'use strict'
  angular.module('service', ['ngResource'])


  #common service
  angular.module('service').factory 'Email', ($resource) ->
    $resource '/signupEmail'