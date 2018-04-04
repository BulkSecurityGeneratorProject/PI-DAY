(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonstergroupDialogController', MonstergroupDialogController);

    MonstergroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Monstergroup'];

    function MonstergroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Monstergroup) {
        var vm = this;

        vm.monstergroup = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.monstergroup.id !== null) {
                Monstergroup.update(vm.monstergroup, onSaveSuccess, onSaveError);
            } else {
                Monstergroup.save(vm.monstergroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myapptestApp:monstergroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
