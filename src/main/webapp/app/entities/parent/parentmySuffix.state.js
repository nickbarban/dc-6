(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parentmySuffix', {
            parent: 'entity',
            url: '/parentmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.parent.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parent/parentsmySuffix.html',
                    controller: 'ParentMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('parent');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('parentmySuffix-detail', {
            parent: 'parentmySuffix',
            url: '/parentmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.parent.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parent/parentmySuffix-detail.html',
                    controller: 'ParentMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('parent');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Parent', function($stateParams, Parent) {
                    return Parent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'parentmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('parentmySuffix-detail.edit', {
            parent: 'parentmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parent/parentmySuffix-dialog.html',
                    controller: 'ParentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parent', function(Parent) {
                            return Parent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parentmySuffix.new', {
            parent: 'parentmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parent/parentmySuffix-dialog.html',
                    controller: 'ParentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstname: null,
                                lastname: null,
                                birthday: null,
                                address: null,
                                phone1: null,
                                phone2: null,
                                email: null,
                                active: false,
                                photoUrl: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('parentmySuffix', null, { reload: 'parentmySuffix' });
                }, function() {
                    $state.go('parentmySuffix');
                });
            }]
        })
        .state('parentmySuffix.edit', {
            parent: 'parentmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parent/parentmySuffix-dialog.html',
                    controller: 'ParentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parent', function(Parent) {
                            return Parent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parentmySuffix', null, { reload: 'parentmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parentmySuffix.delete', {
            parent: 'parentmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parent/parentmySuffix-delete-dialog.html',
                    controller: 'ParentMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Parent', function(Parent) {
                            return Parent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parentmySuffix', null, { reload: 'parentmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
