(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonsterDialogController', MonsterDialogController);

    MonsterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Monster'];

    function MonsterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Monster) {
        var vm = this;

        vm.monster = entity;
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
            if (vm.monster.id !== null) {
                Monster.update(vm.monster, onSaveSuccess, onSaveError);
            } else {
                Monster.save(vm.monster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myapptestApp:monsterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
