(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Monster'];

    function HomeController ($scope, Principal, LoginService, $state, Monster) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.monsters = [];
        vm.selectedMonsters = [];
//        vm.initiative = [];
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        function randomizeMonsters()
        {
            vm.selectedMonsters.sort(function(a,b){return 0.5 - Math.random();});
        }
        function quickEncounter ()
        {
            for (var i = 0; i < vm.monsters.length; i++)
            {  
                switch (userLevel){//need to check the actual name of this variable
                	case "1-4":
                	if (vm.monsters[i].challenge <= 4)
                    {
                    	vm.selectedMonsters.push(vm.monsters[i]);
                    }
                    break;
                    
                    case "5-10":
                	if (vm.monsters[i].challenge >= 5 && vm.monsters[i].challenge <= 10)
                    {
                    	vm.selectedMonsters.push(vm.monsters[i]);
                    }
                    break;
                    
                    case "11-16":
                	if (vm.monsters[i].challenge >= 11 && vm.monsters[i].challenge <= 16)
                    {
                    	vm.selectedMonsters.push(vm.monsters[i]);
                    }
                    break;
                    
                    case "17-20":
                	if (vm.monsters[i].challenge >= 17 && vm.monsters[i].challenge <= 20)
                    {
                    	vm.selectedMonsters.push(vm.monsters[i]);
                    }
                    break;
                }
            }
            randomizeMonsters();
            selectedMonster = vm.selectedMonsters[0];
        }
//        function addPlayerInitiative()
//        {
//            vm.initiative.push(newInit)
//            vm.initiative.sort(function(a,b){
//                if (a.initiative <= b.initiative)
//                {
//                    return(-1);
//                }
//                else
//                {
//                    return(1);
//                }
//            });
//        }
    }
})();
