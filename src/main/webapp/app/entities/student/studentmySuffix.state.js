(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('studentmySuffix', {
            parent: 'entity',
            url: '/studentmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.student.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student/studentsmySuffix.html',
                    controller: 'StudentMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('student');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('studentmySuffix-detail', {
            parent: 'studentmySuffix',
            url: '/studentmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.student.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student/studentmySuffix-detail.html',
                    controller: 'StudentMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('student');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Student', function($stateParams, Student) {
                    return Student.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'studentmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('studentmySuffix-detail.edit', {
            parent: 'studentmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student/studentmySuffix-dialog.html',
                    controller: 'StudentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Student', function(Student) {
                            return Student.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('studentmySuffix.new', {
            parent: 'studentmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student/studentmySuffix-dialog.html',
                    controller: 'StudentMySuffixDialogController',
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
                                userName: null,
                                password: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('studentmySuffix', null, { reload: 'studentmySuffix' });
                }, function() {
                    $state.go('studentmySuffix');
                });
            }]
        })
        .state('studentmySuffix.edit', {
            parent: 'studentmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student/studentmySuffix-dialog.html',
                    controller: 'StudentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Student', function(Student) {
                            return Student.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('studentmySuffix', null, { reload: 'studentmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('studentmySuffix.delete', {
            parent: 'studentmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student/studentmySuffix-delete-dialog.html',
                    controller: 'StudentMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Student', function(Student) {
                            return Student.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('studentmySuffix', null, { reload: 'studentmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
