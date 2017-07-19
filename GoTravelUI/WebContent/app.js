'use strict';

angular.module(
    'tutorialApp',
    [ 'ngAnimate', 'ngRoute', 'ngSanitize', 'tutorialApp.controllers', 'tutorialApp.services',
	'tutorialApp.modalcontroller', 'ui.bootstrap' ]).config([ '$routeProvider', function($routeProvider) {
  $routeProvider.when('/trips/:id', {
    templateUrl : '/GoTravelUI/html/trip.html',
    controller : 'TripCtrl'
  });
  $routeProvider.when('/user-detail/:id', {
    templateUrl : '/GoTravelUI/html/user-detail.html',
    controller : 'UserDetailCtrl'
  });
  $routeProvider.otherwise({
    redirectTo : '/tripsWoot'
  });
} ]);
