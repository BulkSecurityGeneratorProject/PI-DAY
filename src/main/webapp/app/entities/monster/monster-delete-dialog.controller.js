(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonsterDeleteController',MonsterDeleteController);

    MonsterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Monster'];

    function MonsterDeleteController($uibModalInstance, entity, Monster) {
        var vm = this;

        vm.monster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Monster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
