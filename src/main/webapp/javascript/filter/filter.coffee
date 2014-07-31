define ['angular'], ->
  'use strict'
  angular.module('filter', [])


  #common filter
  angular.module('filter').filter 'firstUpperCase', ->
    (word)->
      word.charAt(0).toUpperCase() + word.slice(1);