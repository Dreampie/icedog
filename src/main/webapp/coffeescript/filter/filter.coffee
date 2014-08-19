define ['angular'], ->
  'use strict'
  angular.module('filter', [])

  #first letter  upper case
  .filter 'firstUpperCase', ->
    (word)->
      word.charAt(0).toUpperCase() + word.slice(1);