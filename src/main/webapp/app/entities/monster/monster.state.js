(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('monster', {
            parent: 'entity',
            url: '/monster',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Monsters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monster/monsters.html',
                    controller: 'MonsterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('monster-detail', {
            parent: 'monster',
            url: '/monster/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Monster'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monster/monster-detail.html',
                    controller: 'MonsterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Monster', function($stateParams, Monster) {
                    return Monster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'monster',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('monster-detail.edit', {
            parent: 'monster-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monster/monster-dialog.html',
                    controller: 'MonsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Monster', function(Monster) {
                            return Monster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monster.new', {
            parent: 'monster',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monster/monster-dialog.html',
                    controller: 'MonsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                pageNo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('monster', null, { reload: 'monster' });
                }, function() {
                    $state.go('monster');
                });
            }]
        })
        .state('monster.edit', {
            parent: 'monster',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monster/monster-dialog.html',
                    controller: 'MonsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Monster', function(Monster) {
                            return Monster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monster', null, { reload: 'monster' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monster.delete', {
            parent: 'monster',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monster/monster-delete-dialog.html',
                    controller: 'MonsterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Monster', function(Monster) {
                            return Monster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monster', null, { reload: 'monster' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
