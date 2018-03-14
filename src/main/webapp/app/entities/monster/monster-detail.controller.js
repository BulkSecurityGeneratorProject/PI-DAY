(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonsterDetailController', MonsterDetailController);

    MonsterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Monster'];

    function MonsterDetailController($scope, $rootScope, $stateParams, previousState, entity, Monster) {
        var vm = this;

        vm.monster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myapptestApp:monsterUpdate', function(event, result) {
            vm.monster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
