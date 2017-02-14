(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lessonmySuffix', {
            parent: 'entity',
            url: '/lessonmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.lesson.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lesson/lessonsmySuffix.html',
                    controller: 'LessonMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lesson');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lessonmySuffix-detail', {
            parent: 'lessonmySuffix',
            url: '/lessonmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dancekvartalApp.lesson.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lesson/lessonmySuffix-detail.html',
                    controller: 'LessonMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lesson');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Lesson', function($stateParams, Lesson) {
                    return Lesson.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lessonmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lessonmySuffix-detail.edit', {
            parent: 'lessonmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lessonmySuffix-dialog.html',
                    controller: 'LessonMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lesson', function(Lesson) {
                            return Lesson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lessonmySuffix.new', {
            parent: 'lessonmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lessonmySuffix-dialog.html',
                    controller: 'LessonMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startLesson: null,
                                endLesson: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lessonmySuffix', null, { reload: 'lessonmySuffix' });
                }, function() {
                    $state.go('lessonmySuffix');
                });
            }]
        })
        .state('lessonmySuffix.edit', {
            parent: 'lessonmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lessonmySuffix-dialog.html',
                    controller: 'LessonMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lesson', function(Lesson) {
                            return Lesson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lessonmySuffix', null, { reload: 'lessonmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lessonmySuffix.delete', {
            parent: 'lessonmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lessonmySuffix-delete-dialog.html',
                    controller: 'LessonMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lesson', function(Lesson) {
                            return Lesson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lessonmySuffix', null, { reload: 'lessonmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
