angular.module('othellogameweb', ['ui.bootstrap', 'ngRoute', 'ngAnimate'])
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
    }).otherwise('/');

  })
  .controller('home', function($rootScope, $scope, $http, $location) {
  if($rootScope.user)
  {
    var profileUrl = "/api/userInformations/"+$rootScope.user.userId.toString();
    $http.get(profileUrl).success(function(data){
       $scope.userInformation = data;
       if(data.rankPoints == 0){
         $scope.title = '超级新人';}
       if(data.rankPoints > 0 && data.rankPoints < 100){
         $scope.title= '新人';
       }
     });
     var userGroupUrl = "/api/users/"+$rootScope.user.userId.toString()+"/userGroup";
     $http.get(userGroupUrl).success(function(data, status){
       $rootScope.userGroup = data;
       if($rootScope.userGroup.userGroupName == "ROLE_ADMIN"){
         $rootScope.roleAdmin = true;
       }else{
         $rootScope.roleAdmin = false;
       }
     });
     var gameRecordsUrl = "/api/gameRecords/search/findByUsername?username="+$rootScope.user.username;
     $http.get(gameRecordsUrl).success(function(data, status){
        $scope.gameRecords = data._embedded.gameRecords;
     }).error(function(data, status){

     });
   }
  })
  .controller('login', function($rootScope, $scope, $http, $location){
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
        $scope.error = false;
    };

    $scope.userLogin = function() {
      $scope.alerts = [];
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
           $scope.alerts.push({type:'danger',msg:'登录过程发生了错误，请重试。'});
         });
        };
  })
  .controller('navigation', function($rootScope, $scope, $http, $location) {
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
    $scope.alerts = [];
    $scope.closeAlert = function(index) {
       $scope.alerts.splice(index, 1);
    };
    if($rootScope.user){
      var profileUrl = "/api/userInformations/"+$rootScope.user.userId.toString();
      $http.get(profileUrl).success(function(data, status)
      {
         $scope.userInformation = data;
      });
      $scope.updateProfile = function(){
         $http.patch(profileUrl, $scope.userInformation).success(function(data, status){
           $scope.userInformation = data;
           $scope.alerts.push({type : 'success', msg : '操作成功，您的个人信息已更新。'});
         }).error(function(data, status){
           $scope.alerts.push({type : 'danger', msg : '操作失败，请重试。'});
         });
      }
    }
  })
  .controller('forgetPassword', function($rootScope, $scope, $http, $location){
    $scope.alerts = [];
    $scope.closeAlert = function(index) {
        if($scope.alerts[0].type == 'success'){
          $location.path('/login');
        }
        $scope.alerts.splice(index, 1);
    };
    $scope.findPassword = function(){
      $http.post('/api/user/forgetPassword', $scope.restetPassword).success(function(data, status){
        if(status == 200){
          $scope.alerts.push({type : 'success', msg : '操作成功，已经将帐号的密码重设为新密码。'});
        }
      }).error(function(data, status){
          $scope.alerts.push({type : 'danger', msg : '操作失败，请重试。'});
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
    $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
    };
  })
