(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('discursive-answer', {
            parent: 'entity',
            url: '/discursive-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.discursiveAnswer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discursive-answer/discursive-answers.html',
                    controller: 'DiscursiveAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discursiveAnswer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('discursive-answer-detail', {
            parent: 'entity',
            url: '/discursive-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.discursiveAnswer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discursive-answer/discursive-answer-detail.html',
                    controller: 'DiscursiveAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discursiveAnswer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DiscursiveAnswer', function($stateParams, DiscursiveAnswer) {
                    return DiscursiveAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'discursive-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('discursive-answer-detail.edit', {
            parent: 'discursive-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-answer/discursive-answer-dialog.html',
                    controller: 'DiscursiveAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiscursiveAnswer', function(DiscursiveAnswer) {
                            return DiscursiveAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discursive-answer.new', {
            parent: 'discursive-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-answer/discursive-answer-dialog.html',
                    controller: 'DiscursiveAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                answer: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('discursive-answer', null, { reload: true });
                }, function() {
                    $state.go('discursive-answer');
                });
            }]
        })
        .state('discursive-answer.edit', {
            parent: 'discursive-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-answer/discursive-answer-dialog.html',
                    controller: 'DiscursiveAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiscursiveAnswer', function(DiscursiveAnswer) {
                            return DiscursiveAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discursive-answer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discursive-answer.delete', {
            parent: 'discursive-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-answer/discursive-answer-delete-dialog.html',
                    controller: 'DiscursiveAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DiscursiveAnswer', function(DiscursiveAnswer) {
                            return DiscursiveAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discursive-answer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
