angular.module('othellogameweb', [ 'ngRoute' ])
  .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home',
		controller : 'home'
	}).when('/login', {
		templateUrl : 'login',
		controller : 'navigation'
	}).when('/register', {
	    templateUrl : 'register',
	    controller  : 'navigation'
	}).otherwise('/');

  })
  .controller('home', function($scope, $http) {
    $http.get('/api/user').success(function(data) {
      $scope.user = data;
    })
  })
  .controller('navigation', function($rootScope, $scope, $http, $location) {

    var authenticate = function(credentials, callback) {

        $http.post('/api/authorization', {headers : headers}).success(function(data) {
          if (data.name) {
            $rootScope.authenticated = true;
          } else {
            $rootScope.authenticated = false;
          }
          callback && callback();
        }).error(function() {
          $rootScope.authenticated = false;
          callback && callback();
        });

      }
    $scope.credentials = {};
    $scope.login = function() {
          authenticate($scope.credentials, function() {
            if ($rootScope.authenticated) {
              $location.path("/");
              $scope.error = false;
            } else {
              $location.path("/login");
              $scope.error = true;
            }
          });
      };
    $scope.logout = function() {
       $http.post('logout', {}).success(function() {
         $rootScope.authenticated = false;
         $location.path("/");
       }).error(function(data) {
         $rootScope.authenticated = false;
       });
     }
  })