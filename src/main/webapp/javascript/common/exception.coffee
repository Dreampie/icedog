define ['angular'], ->
  'use strict'
  angular.module('exception', [])

  angular.module('exception').factory 'exceptionFactory', ($injector) ->
    ($delegate) ->
      (exception, cause) ->
        # Lazy load notifications to get around circular dependency
        #Circular dependency: $rootScope <- notification <- messageNotification <- exception
        messageNotification = $injector.get('messageNotification')

        # Pass through to original handler
        $delegate(exception, cause)

        # Push a notification error
        messageNotification.pushForCurrentRoute('error.fatal', 'error', {}, {
          exception: exception,
          cause: cause
        })

  .config '$provide', ($provide) ->
    $provide.decorator('$exception', ['$delegate', 'exceptionFactory', ($delegate, exceptionFactory) ->
      exceptionFactory($delegate)
    ])

