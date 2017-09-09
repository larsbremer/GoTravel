'use strict';

angular.module(
    'tutorialApp',
    [ 'ngAnimate', 'ngRoute', 'ngSanitize', 'tutorialApp.controllers', 'tutorialApp.services',
	'tutorialApp.modalcontroller', 'ui.bootstrap' ]).config([ '$routeProvider', function($routeProvider) {
  $routeProvider.when('/trips/:id', {
    templateUrl : '/GoTravelUI/app/components/trip/tripView.html',
    controller : 'TripCtrl'
  });
  $routeProvider.when('/user-detail/:id', {
    templateUrl : 'user-detail.html',
    controller : 'UserDetailCtrl'
  });
  $routeProvider.otherwise({
    redirectTo : '/tripsWoot'
  });
} ]);
