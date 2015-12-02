angular.module('othellogameweb', [ 'ngRoute', 'ngAnimate'])
  .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : '/partials/home',
		controller : 'home'
	}).when('/login', {
		templateUrl : '/partials/login',
		controller : 'navigation'
	}).when('/admin', {
    	    templateUrl : '/partials/adminTool',
    	    controller  : 'admin'
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
    $http.get(profileUrl).success(function(data){
       $scope.userInformation = data;
     });
     var userGroupUrl = "/api/users/"+$rootScope.user.userId.toString()+"/userGroup";
     $http.get(userGroupUrl).success(function(data){
       $rootScope.userGroup = data;
       if($rootScope.userGroup.userGroupName == "ROLE_ADMIN"){
         $rootScope.roleAdmin = true;
       }
     });
   }
  })
  .controller('navigation', function($rootScope, $scope, $http, $location) {

    $scope.userLogin = function() {
       $http.post('/api/authorization', $scope.login).success(function(data, status){
         $rootScope.user = data;
         if(data.username){
              $rootScope.authenticated = true;
              $rootScope.roleAdmin = false;
              $location.path("/");
              $scope.error = false;
            }else{
              $rootScope.authenticated = false;
              $scope.error = true;
            }
       }).error(function(data, status){
         $scope.error = true;
       });
      };
    $scope.logout = function() {
       $http.post('/api/logout', {}).success(function() {
         $rootScope.authenticated = false;
         $rootScope.roleAdmin = false;
         $location.path("/");
       }).error(function(data, status) {
         $rootScope.authenticated = false;
       });
     }
     $scope.registerUser = function(){
       $http.post('/api/register', $scope.register).success(function()
       {
         $rootScope.authenticated = false;
         $location.path("/");
         $scope.error = false;
       });
     }
  })
  .controller('profile', function($rootScope, $scope, $http){
    if($rootScope.user){
      var profileUrl = "/api/userInformations/"+$rootScope.user.userId.toString();
      $http.get(profileUrl).success(function(data, status)
      {
         $scope.userInformation = data;
       });
      $scope.updateProfile = function(){
         $http.patch(profileUrl, $scope.userInformation).success(function(data, status){
           $scope.userInformation = data;
           $scope.updateSuccess = true;
         }).error(function(data, status){
           console.log(status);
           $scope.updateSuccess = false;
         });
      }
    }
  })
  .controller('admin', function($rootScope, $scope, $http){
    if($rootScope.user){
      $http.get("/api/users").success(function(data, status){
         $scope.users = data._embedded.users;
      });
    }
  })