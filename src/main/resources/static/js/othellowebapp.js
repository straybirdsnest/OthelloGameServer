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
           $scope.alerts.push({type:'danger',msg:'登录过程发生了错误，请重试！'});
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
      $scope.today = function() {
          $scope.dt = new Date();
      };
      $scope.today();

      $scope.clear = function () {
        $scope.dt = null;
      };

      // Disable weekend selection
      $scope.disabled = function(date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
      };

      $scope.toggleMin = function() {
        $scope.minDate = $scope.minDate ? null : new Date();
      };
      $scope.toggleMin();
      $scope.maxDate = new Date(2020, 5, 22);

      $scope.open = function($event) {
        $scope.status.opened = true;
      };

      $scope.setDate = function(year, month, day) {
        $scope.dt = new Date(year, month, day);
      };

      $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
      };

      $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
      $scope.format = $scope.formats[0];

      $scope.status = {
        opened: false
      };

      var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 2);
        $scope.events =
        [
          {
            date: tomorrow,
            status: 'full'
          },
          {
            date: afterTomorrow,
            status: 'partially'
          }
        ];

        $scope.getDayClass = function(date, mode) {
          if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0,0,0,0);

            for (var i=0;i<$scope.events.length;i++){
              var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

              if (dayToCheck === currentDay) {
                return $scope.events[i].status;
              }
            }
        }

        return '';
      };
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