define ['angular','local'], ->
  'use strict'
  angular.module('notification', [])


  angular.module('notification').factory 'notification', ($rootScope)->
    notification =
      'STICKY': []
      'ROUTE_CURRENT': []
      'ROUTE_NEXT': []

    notificationService = {}

    addNotification = (notificationArray, notificationObj)->
      if !angular.isObject(notificationObj)
        throw new Error("Only object can be added to the notification service")

      notificationArray.push(notificationObj)

      notificationObj


    $rootScope.$on '$routeChangeSuccess', ->
      notification.ROUTE_CURRENT.length = 0;
      notification.ROUTE_CURRENT = angular.copy notification.ROUTE_NEXT
      notification.ROUTE_NEXT.length = 0;


    notificationService.getCurrent = ->
      [].concat(notification.STICKY, notification.ROUTE_CURRENT)


    notificationService.pushSticky = (notification) ->
      addNotification(notification.STICKY, notification)


    notificationService.pushForCurrentRoute = (notification) ->
      addNotification(notification.ROUTE_CURRENT, notification)


    notificationService.pushForNextRoute = (notification) ->
      addNotification(notification.ROUTE_NEXT, notification)


    notificationService.remove = (notification) ->
      angular.forEach(notification, (notificationByType)->
        idx = notificationByType.indexOf(notification)
        if (idx > -1)
          notificationByType.splice(idx, 1)
      )


    notificationService.removeAll = ->
      angular.forEach(notification, (notificationByType)->
        notificationByType.length = 0
      )

    notificationService

  .factory 'messageNotification', (notification,local)->
    prepareNotification = (msgKey, type, interpolateParams, otherProperties) ->
      angular.extend
        message: local.get('message',msgKey, interpolateParams)
        type: type,
        otherProperties


    messageNotification =
      pushSticky: (msgKey, type, interpolateParams, otherProperties) ->
        notification.pushSticky(prepareNotification(msgKey, type, interpolateParams, otherProperties))

      pushForCurrentRoute: (msgKey, type, interpolateParams, otherProperties) ->
        notification.pushForCurrentRoute(prepareNotification(msgKey, type, interpolateParams, otherProperties))

      pushForNextRoute: (msgKey, type, interpolateParams, otherProperties) ->
        notification.pushForNextRoute(prepareNotification(msgKey, type, interpolateParams, otherProperties))

      getCurrent: () ->
        notification.getCurrent()

      remove: (notification) ->
        notification.remove(notification)


    messageNotification
