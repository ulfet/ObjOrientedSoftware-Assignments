'use strict';

// Declare app level module which depends on views, and components
angular.module('swcarchitect', [
    'ngRoute',
    'swcarchitect.images',
    'ui.bootstrap',
    'ngAnimate'
]).
config(['$routeProvider', function ($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/images'});
}]);
