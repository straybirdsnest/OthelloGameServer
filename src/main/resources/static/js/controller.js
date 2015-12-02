angular.module('othellogameweb', [ 'ngRoute', 'ngAnimate'])
  .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : '/partials/home',
		controller : 'home'
	}).when('/login', {
		templateUrl : '/partials/login',
		controller : 'navigation'
	}).when('/register', {
	    templateUrl : '/partials/register',
	    controller  : 'navigation'
	}).when('/profile',{
	    templateUrl : '/partials/user/profile',
	    controller : 'profile'
	}).otherwise('/');

  })
  .controller('home', function($rootScope, $scope, $http) {
  if($rootScope.user)
  {
    var profileUrl = "/api/userInformations/"+$rootScope.user.userId.toString();
    $http.get(profileUrl).success(function(data)
    {
       $scope.userInformation = data;
     });
   }
  })
  .controller('navigation', function($rootScope, $scope, $http, $location) {

    $scope.userLogin = function() {
       $http.post('/api/authorization', $scope.login).success(function(data){
         $rootScope.user = data;
         if(data.username){
              $rootScope.authenticated = true;
              $location.path("/");
              $scope.error = false;
            }else{
              $rootScope.authenticated = false;
              $scope.error = true;
            }
       })
      };
    $scope.logout = function() {
       $http.post('/api/logout', {}).success(function() {
         $rootScope.authenticated = false;
         $location.path("/");
       }).error(function(data) {
         $rootScope.authenticated = false;
       });
     }
     $scope.registerUser = function(){
       $http.post('/api/register', $scope.register).success(function()
       {
         console.log('register ok');
       });
     }
  })
  .controller('profile', function($rootScope, $scope, $http){
    if($rootScope.user)
      {
        var profileUrl = "/api/userInformations/"+$rootScope.user.userId.toString();
        $http.get(profileUrl).success(function(data)
        {
           $scope.userInformation = data;
         });
       }
  })