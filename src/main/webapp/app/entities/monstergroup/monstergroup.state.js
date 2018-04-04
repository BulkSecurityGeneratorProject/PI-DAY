(function() {
    'use strict';

    angular
        .module('myapptestApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('monstergroup', {
            parent: 'entity',
            url: '/monstergroup',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Monstergroups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monstergroup/monstergroups.html',
                    controller: 'MonstergroupController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('monstergroup-detail', {
            parent: 'monstergroup',
            url: '/monstergroup/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Monstergroup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monstergroup/monstergroup-detail.html',
                    controller: 'MonstergroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Monstergroup', function($stateParams, Monstergroup) {
                    return Monstergroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'monstergroup',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('monstergroup-detail.edit', {
            parent: 'monstergroup-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monstergroup/monstergroup-dialog.html',
                    controller: 'MonstergroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Monstergroup', function(Monstergroup) {
                            return Monstergroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monstergroup.new', {
            parent: 'monstergroup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monstergroup/monstergroup-dialog.html',
                    controller: 'MonstergroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                monsters: null,
                                user: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('monstergroup', null, { reload: 'monstergroup' });
                }, function() {
                    $state.go('monstergroup');
                });
            }]
        })
        .state('monstergroup.edit', {
            parent: 'monstergroup',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monstergroup/monstergroup-dialog.html',
                    controller: 'MonstergroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Monstergroup', function(Monstergroup) {
                            return Monstergroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monstergroup', null, { reload: 'monstergroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monstergroup.delete', {
            parent: 'monstergroup',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monstergroup/monstergroup-delete-dialog.html',
                    controller: 'MonstergroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Monstergroup', function(Monstergroup) {
                            return Monstergroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monstergroup', null, { reload: 'monstergroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
