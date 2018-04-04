(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonstergroupDeleteController',MonstergroupDeleteController);

    MonstergroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'Monstergroup'];

    function MonstergroupDeleteController($uibModalInstance, entity, Monstergroup) {
        var vm = this;

        vm.monstergroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Monstergroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
