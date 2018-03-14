(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .controller('MonsterController', MonsterController);

    MonsterController.$inject = ['Monster'];

    function MonsterController(Monster) {

        var vm = this;

        vm.monsters = [];

        loadAll();

        function loadAll() {
            Monster.query(function(result) {
                vm.monsters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
