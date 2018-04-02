(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonstergroupController', MonstergroupController);

    MonstergroupController.$inject = ['Monstergroup'];

    function MonstergroupController(Monstergroup) {

        var vm = this;

        vm.monstergroups = [];

        loadAll();

        function loadAll() {
            Monstergroup.query(function(result) {
                vm.monstergroups = result;
                vm.searchQuery = null;
            });
        }
    }
})();
