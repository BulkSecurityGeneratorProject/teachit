(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lesson', {
            parent: 'entity',
            url: '/lesson',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.lesson.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lesson/lessons.html',
                    controller: 'LessonController',
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
        .state('lesson-detail', {
            parent: 'entity',
            url: '/lesson/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.lesson.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lesson/lesson-detail.html',
                    controller: 'LessonDetailController',
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
                        name: $state.current.name || 'lesson',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lesson-detail.edit', {
            parent: 'lesson-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lesson-dialog.html',
                    controller: 'LessonDialogController',
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
        .state('lesson.new', {
            parent: 'lesson',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lesson-dialog.html',
                    controller: 'LessonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ordering: null,
                                startDate: null,
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lesson', null, { reload: true });
                }, function() {
                    $state.go('lesson');
                });
            }]
        })
        .state('lesson.edit', {
            parent: 'lesson',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lesson-dialog.html',
                    controller: 'LessonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lesson', function(Lesson) {
                            return Lesson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lesson', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lesson.delete', {
            parent: 'lesson',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lesson/lesson-delete-dialog.html',
                    controller: 'LessonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lesson', function(Lesson) {
                            return Lesson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lesson', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
