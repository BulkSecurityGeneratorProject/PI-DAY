(function() {
    'use strict';
    angular
        .module('myapptestApp')
        .factory('Monster', Monster);

    Monster.$inject = ['$resource'];

    function Monster ($resource) {
        var resourceUrl =  'api/monsters/:id';

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
