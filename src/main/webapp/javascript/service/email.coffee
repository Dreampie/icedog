define ['service'], ->
  'use strict'
  angular.module('services').factory 'EmailService', ($resource) ->
    $resource '/signupEmail'