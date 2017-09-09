'use strict';

var app = angular.module('tutorialApp.controllers', []);

app.controller('UserDetailCtrl', [ '$scope', '$routeParams', 'UserFactory', '$location',
    function($scope, $routeParams, UserFactory, $location) {

      // callback for ng-click 'updateUser':
      $scope.updateUser = function() {
	UserFactory.update($scope.user);
	$location.path('/user-list');
      };

      // callback for ng-click 'cancel':
      $scope.cancel = function() {
	$location.path('/user-list');

      };

      $scope.user = UserFactory.show({
	id : $routeParams.id
      });
    } ]);

app.controller('TripCtrl', [ '$scope', '$http', '$location', '$routeParams', function($scope, $http, $location) {

  var tripId = $location.path()

  $http.get('/GoTravelBackend/rest' + $location.path() + '?expand=true').then(function(tripResponse) {
    $scope.trip = []
    var trip = tripResponse.data

    var timeFormat = "YYYY-MM-DD'T'HH:mm:ss.SSSZ"

    for ( var segmentId in trip.segments) {

      var startDate = trip.segments[segmentId].startDate
      var startDateObj = moment.utc(startDate, timeFormat)

      var endDate = trip.segments[segmentId].endDate
      var endDateObj = moment.utc(endDate, timeFormat)

      trip.segments[segmentId].startDate = startDateObj.format('HH:mm');
      trip.segments[segmentId].endDate = endDateObj.format('HH:mm');

      trip.segments[segmentId].startDateReduced = startDateObj.format('ddd, DD.MM.');
      trip.segments[segmentId].endDateReduced = endDateObj.format('ddd, DD.MM.');

      var durationMinutes = Math.round((endDateObj.format('x') - startDateObj.format('x')) / (1000 * 60));

      var days = Math.floor(durationMinutes / (24 * 60))
      var remainingMinutes = durationMinutes - (days * 24 * 60)
      var hours = Math.floor(remainingMinutes / 60)
      var remainingMinutes = remainingMinutes - (hours * 60)
      var minutes = remainingMinutes

      var durationString = ""
      if (days > 0) {
	durationString += days + "d "
      }

      if (days > 0 || hours > 0) {
	durationString += hours + "h "
      }

      durationString += minutes + "min"

      trip.segments[segmentId].duration = durationString

      $scope.trip.push(trip.segments[segmentId])
      if (trip.segments[segmentId].eveningAccomodation != null) {
	$scope.trip.push(trip.segments[segmentId].eveningAccomodation)
      }
    }
  });
} ])
