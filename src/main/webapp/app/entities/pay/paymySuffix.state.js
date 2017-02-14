(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('paymySuffix', {
            parent: 'entity',
            url: '/paymySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.pay.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pay/paysmySuffix.html',
                    controller: 'PayMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pay');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('paymySuffix-detail', {
            parent: 'paymySuffix',
            url: '/paymySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.pay.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pay/paymySuffix-detail.html',
                    controller: 'PayMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pay');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pay', function($stateParams, Pay) {
                    return Pay.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'paymySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('paymySuffix-detail.edit', {
            parent: 'paymySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pay/paymySuffix-dialog.html',
                    controller: 'PayMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pay', function(Pay) {
                            return Pay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paymySuffix.new', {
            parent: 'paymySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pay/paymySuffix-dialog.html',
                    controller: 'PayMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                sum: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('paymySuffix', null, { reload: 'paymySuffix' });
                }, function() {
                    $state.go('paymySuffix');
                });
            }]
        })
        .state('paymySuffix.edit', {
            parent: 'paymySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pay/paymySuffix-dialog.html',
                    controller: 'PayMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pay', function(Pay) {
                            return Pay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paymySuffix', null, { reload: 'paymySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paymySuffix.delete', {
            parent: 'paymySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pay/paymySuffix-delete-dialog.html',
                    controller: 'PayMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pay', function(Pay) {
                            return Pay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paymySuffix', null, { reload: 'paymySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
