angular.module('othellogameweb', [ 'ngRoute', 'ngAnimate'])
  .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/home', {
		templateUrl : '/partials/home',
		controller : 'home'
	}).when('/login', {
		templateUrl : '/partials/login',
		controller : 'login'
	}).when('/admin', {
    	templateUrl : '/partials/adminTool',
    	controller : 'admin'
    }).when('/register', {
	    templateUrl : '/partials/register',
	    controller  : 'register'
	}).when('/profile',{
	    templateUrl : '/partials/user/profile',
	    controller : 'profile'
	}).when('/forgetPassword',{
      	    templateUrl : '/partials/forgetPassword',
      	    controller : 'forgetPassword'
    }).otherwise('/home');

  })
  .controller('home', function($rootScope, $scope, $http, $location) {
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
       }else{
         $rootScope.roleAdmin = false;
       }
     });
   }
  })
  .controller('login', function($rootScope, $scope, $http, $location){
    $scope.userLogin = function() {
         $http.post('/api/authorization', $scope.login).success(function(data, status){
           $rootScope.user = data;
           if(data.username){
                $rootScope.authenticated = true;
                $rootScope.roleAdmin = false;
                $location.path("/home");
                $scope.error = false;
              }else{
                $rootScope.authenticated = false;
                $scope.error = true;
              }
         }).error(function(data, status){
           $scope.error = true;
         });
        };
  })
  .controller('navigation', function($rootScope, $scope, $http, $location) {
    $scope.isActive = function (viewLocation) {
      return $location.path().indexOf(viewLocation) == 0;
    };
    $scope.logout = function() {
       if($rootScope.user){
             var logout = new Object();
             logout.userId = $rootScope.user.userId;
             $http.post('/api/logout', logout).success(function() {
               $rootScope.user = null;
               $rootScope.userInformation = null;
               $rootScope.authenticated = false;
               $rootScope.roleAdmin = false;
               $location.path("/home");
             }).error(function(data, status) {
               $rootScope.authenticated = false;
             });
        }
      }
  })
  .controller('profile', function($rootScope, $scope, $http, $location){
    if($rootScope.user){
      var profileUrl = "/api/userInformations/"+$rootScope.user.userId.toString();
      $http.get(profileUrl).success(function(data, status)
      {
         $scope.userInformation = data;
       });
      $scope.updateProfile = function(){
         $http.patch(profileUrl, $scope.userInformation).success(function(data, status){
           $scope.userInformation = data;
           $scope.success = true;
           $scope.error = false;
         }).error(function(data, status){
           console.log(status);
           $scope.success = false;
           $scope.error = true;
         });
      }
    }
  })
  .controller('forgetPassword', function($rootScope, $scope, $http, $location){
    $scope.findPassword = function(){
      $http.post('/api/user/forgetPassword', $scope.restetPassword).success(function(data, status){
        if(status == 200){
          $scope.success = true;
          $scope.error = false;
        }
      }).error(function(data, status){
        $scope.success = false;
        $scope.error = true;
      });
    }
  })
  .controller('admin', function($rootScope, $scope, $http){
    if($rootScope.user){
      $http.get("/api/users").success(function(data, status){
         $scope.users = data._embedded.users;
      });
    }
  })
  .controller('register', function($rootScope, $scope, $http, $location){
    $scope.registerUser = function(){
         $http.post('/api/register', $scope.register).success(function(data, status){
           $rootScope.authenticated = false;
           $location.path("/home");
           $scope.error = false;
         }).error(function(data, status){
           $scope.error = true;
         });
       }
  })