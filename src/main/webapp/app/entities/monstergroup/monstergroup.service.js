(function() {
    'use strict';
    angular
        .module('myapptestApp')
        .factory('Monstergroup', Monstergroup);

    Monstergroup.$inject = ['$resource'];

    function Monstergroup ($resource) {
        var resourceUrl =  'api/monstergroups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
