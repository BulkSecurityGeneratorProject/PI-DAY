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
        vm.quickEncounter = quickEncounter;
        vm.advancedEncounter = advancedEncounter;
        vm.monsters = [];
        vm.inputEnvironment = 'Default';
        vm.userLevel = 0;
        vm.selectedMonsters = [];
        vm.tempMonsters = [];
        vm.advancedUserLevel = 0;
        vm.advancedUserNumber = 0;
        vm.notEnoughMonsters = false;

//        vm.initiative = [];
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
	loadAll();

        function loadAll() {
            Monster.query(function(result) {
                vm.monsters = result;
                vm.searchQuery = null;
            });
        }
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
            vm.tempMonsters.sort(function(a,b){return 0.5 - Math.random();});
        }
        function quickEncounter ()
        {
            vm.selectedMonsters = [];
            for (var i = 0; i < vm.monsters.length; i++)
            {
                switch (vm.userLevel){//need to check the actual name of this variable
                	case "1-4":
                	if (vm.monsters[i].challenge <= 4)
                    {
                    	vm.tempMonsters.push(vm.monsters[i]);
                    }
                    break;

                    case "5-10":
                	if (vm.monsters[i].challenge >= 5 && vm.monsters[i].challenge <= 10)
                    {
                    	vm.tempMonsters.push(vm.monsters[i]);
                    }
                    break;

                    case "11-16":
                	if (vm.monsters[i].challenge >= 11 && vm.monsters[i].challenge <= 16)
                    {
                    	vm.tempMonsters.push(vm.monsters[i]);
                    }
                    break;

                    case "17-20":
                	if (vm.monsters[i].challenge >= 17 && vm.monsters[i].challenge <= 20)
                    {
                    	vm.tempMonsters.push(vm.monsters[i]);
                    }
                    break;
                }
            }
            randomizeMonsters();
            for(var i=0; i<3;i++)
            {
                vm.selectedMonsters.push(vm.tempMonsters[i]);
            }
        }
        function advancedEncounter ()
        {
            vm.selectedMonsters = [];
            vm.tempMonsters = [];
            for (var i = 0; i < vm.monsters.length; i++)
            {
                if (vm.monsters[i].environment === vm.inputEnvironment)
                {
                    switch (vm.advancedUserLevel){//need to check the actual name of this variable
                	case "1-4":
                	if (vm.monsters[i].challenge <= 4)
                        {
                            vm.tempMonsters.push(vm.monsters[i]);
                        }
                    break;

                    case "5-10":
                	if (vm.monsters[i].challenge >= 5 && vm.monsters[i].challenge <= 10)
                        {
                            vm.tempMonsters.push(vm.monsters[i]);
                        }
                    break;

                    case "11-16":
                	if (vm.monsters[i].challenge >= 11 && vm.monsters[i].challenge <= 16)
                        {
                        	vm.tempMonsters.push(vm.monsters[i]);
                        }
                    break;

                    case "17-20":
                    if (vm.monsters[i].challenge >= 17 && vm.monsters[i].challenge <= 20)
                        {
                            vm.tempMonsters.push(vm.monsters[i]);
                        }
                    break;
                    }
                }
            }
            randomizeMonsters();
            console.log(vm.tempMonsters);
            if(vm.tempMonsters.length < vm.advancedUserNumber)
            {
                vm.notEnoughMonsters = true;
            }
            else {
                for(var i=0;i<vm.advancedUserNumber;i++)
                {
                    vm.selectedMonsters.push(vm.tempMonsters[i]);
                }
                vm.notEnoughMonsters= false;
            }
            console.log(vm.selectedMonsters);
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
