'use strict';

var app = angular.module('tutorialApp.controllers', []);

// app.controller('UserListCtrl', ['$scope', 'UsersFactory', 'UserFactory',
// '$location',
// function ($scope, UsersFactory, UserFactory, $location) {
//
// // callback for ng-click 'editUser':
// $scope.editUser = function (userId) {
// $location.path('/user-detail/' + userId);
// };
//
// // callback for ng-click 'deleteUser':
// $scope.deleteUser = function (userId) {
// UserFactory.delete({ id: userId });
// $scope.users = UsersFactory.query();
// };
//
// // callback for ng-click 'createUser':
// $scope.createNewUser = function () {
// $location.path('/user-creation');
// };
//
// $scope.users = UsersFactory.query();
// }]);

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

  var tripId = $location.search().tripid

  // callback for ng-click 'editUser':
  $scope.editUser = function(userId) {
    $location.path('/user-detail/' + userId);
  };

  $http.get('/RestApplicationBlueprint/rest/trip/' + tripId + '?expand=true').then(function(tripResponse) {
    $scope.trip = []
    var trip = tripResponse.data

    for ( var segmentId in trip.segments) {
      var startDate = trip.segments[segmentId].startDate
      var startDateObj = new Date(startDate)

      var endDate = trip.segments[segmentId].endDate
      var endDateObj = new Date(endDate)

      var options = {
	hour : 'numeric',
	minute : 'numeric',
	timeZone : 'UTC'
      };

      trip.segments[segmentId].startDate = startDateObj.toLocaleString('de-DE', options);
      trip.segments[segmentId].endDate = endDateObj.toLocaleString('de-DE', options);

      var reducedOptions = {
	month : 'numeric',
	day : 'numeric',
	timeZone : 'UTC',
	weekday : 'short'
      };

      trip.segments[segmentId].startDateReduced = startDateObj.toLocaleString('de-DE', reducedOptions);
      trip.segments[segmentId].endDateReduced = endDateObj.toLocaleString('de-DE', reducedOptions);

      var durationMinutes = Math.round((endDateObj.getTime() - startDateObj.getTime()) / (1000 * 60));

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
