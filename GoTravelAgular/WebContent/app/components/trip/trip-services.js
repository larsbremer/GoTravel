'use strict';

var services = angular.module('tutorialApp.services', ['ngResource']);

services.factory('DummyFactory', function ($resource) {
    return $resource('/ngdemo/web/dummy', {}, {
        query: { method: 'GET', params: {}, isArray: false }
    })
});

services.factory('UsersFactory', function ($resource) {
    return $resource('/ngdemo/web/users', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    })
});

services.factory('UserFactory', function ($resource) {
    return $resource('/ngdemo/web/users/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});
