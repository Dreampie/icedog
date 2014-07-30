define ['filter'], ->
  'use strict'
  angular.module('filter', []).filter 'firstUpperCase', ()->
    (word)->
      word.replace(/\s[a-z]/g, ($1) ->
        $1.toLocaleUpperCase()
      ).replace(/^[a-z]/, ($1)->
        $1.toLocaleUpperCase()
      )