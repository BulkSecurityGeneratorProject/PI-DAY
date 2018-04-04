(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonstergroupDetailController', MonstergroupDetailController);

    MonstergroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Monstergroup'];

    function MonstergroupDetailController($scope, $rootScope, $stateParams, previousState, entity, Monstergroup) {
        var vm = this;

        vm.monstergroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myapptestApp:monstergroupUpdate', function(event, result) {
            vm.monstergroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
