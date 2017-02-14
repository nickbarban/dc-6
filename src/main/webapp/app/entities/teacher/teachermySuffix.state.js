(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('teachermySuffix', {
            parent: 'entity',
            url: '/teachermySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.teacher.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/teacher/teachersmySuffix.html',
                    controller: 'TeacherMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('teacher');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('teachermySuffix-detail', {
            parent: 'teachermySuffix',
            url: '/teachermySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.teacher.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/teacher/teachermySuffix-detail.html',
                    controller: 'TeacherMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('teacher');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Teacher', function($stateParams, Teacher) {
                    return Teacher.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'teachermySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('teachermySuffix-detail.edit', {
            parent: 'teachermySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher/teachermySuffix-dialog.html',
                    controller: 'TeacherMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Teacher', function(Teacher) {
                            return Teacher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('teachermySuffix.new', {
            parent: 'teachermySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher/teachermySuffix-dialog.html',
                    controller: 'TeacherMySuffixDialogController',
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
                                active: false,
                                email: null,
                                photoUrl: null,
                                userName: null,
                                password: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('teachermySuffix', null, { reload: 'teachermySuffix' });
                }, function() {
                    $state.go('teachermySuffix');
                });
            }]
        })
        .state('teachermySuffix.edit', {
            parent: 'teachermySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher/teachermySuffix-dialog.html',
                    controller: 'TeacherMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Teacher', function(Teacher) {
                            return Teacher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('teachermySuffix', null, { reload: 'teachermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('teachermySuffix.delete', {
            parent: 'teachermySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher/teachermySuffix-delete-dialog.html',
                    controller: 'TeacherMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Teacher', function(Teacher) {
                            return Teacher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('teachermySuffix', null, { reload: 'teachermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
