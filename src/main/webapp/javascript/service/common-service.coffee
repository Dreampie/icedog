define ['service'], ->
  'use strict'
  angular.module('service').factory 'EmailService', ($resource) ->
    $resource '/signupEmail'