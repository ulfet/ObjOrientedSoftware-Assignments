'use strict';

angular.module('swcarchitect.images', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/images', {
    templateUrl: 'images/images.html',
    controller: 'ImageCtrl'
  });
}])

.controller('ImageCtrl', function($scope,$http) {

  $http.get('http://localhost:8080/image').
  success(function(data) {
    $scope.images = data;
  });

});