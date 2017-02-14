(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subjectmySuffix', {
            parent: 'entity',
            url: '/subjectmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.subject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subject/subjectsmySuffix.html',
                    controller: 'SubjectMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subject');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subjectmySuffix-detail', {
            parent: 'subjectmySuffix',
            url: '/subjectmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.subject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subject/subjectmySuffix-detail.html',
                    controller: 'SubjectMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subject');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Subject', function($stateParams, Subject) {
                    return Subject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'subjectmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('subjectmySuffix-detail.edit', {
            parent: 'subjectmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subjectmySuffix-dialog.html',
                    controller: 'SubjectMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subject', function(Subject) {
                            return Subject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subjectmySuffix.new', {
            parent: 'subjectmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subjectmySuffix-dialog.html',
                    controller: 'SubjectMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                active: false,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subjectmySuffix', null, { reload: 'subjectmySuffix' });
                }, function() {
                    $state.go('subjectmySuffix');
                });
            }]
        })
        .state('subjectmySuffix.edit', {
            parent: 'subjectmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subjectmySuffix-dialog.html',
                    controller: 'SubjectMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subject', function(Subject) {
                            return Subject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subjectmySuffix', null, { reload: 'subjectmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subjectmySuffix.delete', {
            parent: 'subjectmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subjectmySuffix-delete-dialog.html',
                    controller: 'SubjectMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subject', function(Subject) {
                            return Subject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subjectmySuffix', null, { reload: 'subjectmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
