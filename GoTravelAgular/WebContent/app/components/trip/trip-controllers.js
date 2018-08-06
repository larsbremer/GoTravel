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

  function getDateRangeString(currentDaySegments) {

    var startDate = null;
    var endDate = null;

    for ( var segmentId in currentDaySegments) {

      var segment = currentDaySegments[segmentId]
console.log(segmentId)
      if (segmentId == 0) {
	startDate = segment.startDateReduced
      }

      if (segment.type != 'accommodation') {
	endDate = segment.endDateReduced
      }
    }

    if (startDate == endDate) {
      return [ startDate ];
    }

    return [ startDate, endDate ];
  }

  function setDateAttributes(segment) {

    var timeFormat = "YYYY-MM-DD'T'HH:mm:ss.SSSZ"

    var startDate = segment.startDate
    var startDateObj = moment.utc(startDate, timeFormat)

    var endDate = segment.endDate
    var endDateObj = moment.utc(endDate, timeFormat)

    segment.startDate = startDateObj.format('HH:mm');
    segment.endDate = endDateObj.format('HH:mm');

    segment.startDateReduced = startDateObj.format('ddd, DD.MM.');
    segment.endDateReduced = endDateObj.format('ddd, DD.MM.');

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

    segment.duration = durationString

  }

  function setAttributes(segment) {

    var displayedAttributes = {
      "flight" : [ "seat", "airplane", "url" ],
      "busride" : [ "number", "url" ],
      "trainride" : [ "url" ],
      "accommodation" : [ "url" ]
    }

    var messageCatalog = {
      "flight" : "Flight",
      "seat" : "Seat",
      "airplane" : "Airplane",
      "accommodation" : "Accommodation",
      "url" : "URL",
      "trainride" : "Train Ride",
      "busride" : "Bus Ride",
      "number" : "Number"
    }

    // Create map of properties
    if (segment.type in displayedAttributes) {

      var attributes = {};

      for ( var internalAttrId in displayedAttributes[segment.type]) {

	var internalAttrName = displayedAttributes[segment.type][internalAttrId]
	var displayAttrName = messageCatalog[internalAttrName]
	attributes[displayAttrName] = segment[internalAttrName]
	if (attributes[displayAttrName] == null) {
	  attributes[displayAttrName] = "-"
	}
      }

      segment.attributes = attributes
    }

  }

  $http.get('/GoTravelBackend/rest' + $location.path() + '?expand=true').then(function(tripResponse) {

    var allSegments = []
    var trip = tripResponse.data

    for ( var segmentId in trip.segments) {

      var segment = trip.segments[segmentId]

      setDateAttributes(segment)
      setAttributes(segment)

      // Make evening accommodation one element
      allSegments.push(trip.segments[segmentId])
      if (trip.segments[segmentId].eveningAccommodation != null) {
	var eveningAccommodation = trip.segments[segmentId].eveningAccommodation

	setDateAttributes(eveningAccommodation)
	setAttributes(eveningAccommodation)

	allSegments.push(eveningAccommodation)
      }

    }

    var currentDaySegments = []
    var currentDay = null
    $scope.trip = []
    $scope.day = []

    for ( var segmentId in allSegments) {

      var segment = allSegments[segmentId]

      var isOneDaySegment = segment.startDateReduced == segment.endDateReduced;
      var isSameDayAsBefore = currentDay == segment.startDateReduced;
      if ((isOneDaySegment && isSameDayAsBefore) || segment.type == 'accommodation') {

	currentDaySegments.push(segment);

      } else {

	if (currentDaySegments.length > 0) {
	  $scope.trip.push(currentDaySegments);
	  $scope.day.push(getDateRangeString(currentDaySegments))
	}

	currentDaySegments = [ segment ]
	currentDay = segment.startDateReduced
      }

    }

    $scope.trip.push(currentDaySegments);
    $scope.day.push(getDateRangeString(currentDaySegments))

  });
} ])
