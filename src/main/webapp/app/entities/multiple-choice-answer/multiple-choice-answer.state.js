(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('multiple-choice-answer', {
            parent: 'entity',
            url: '/multiple-choice-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.multipleChoiceAnswer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/multiple-choice-answer/multiple-choice-answers.html',
                    controller: 'MultipleChoiceAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('multipleChoiceAnswer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('multiple-choice-answer-detail', {
            parent: 'entity',
            url: '/multiple-choice-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.multipleChoiceAnswer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/multiple-choice-answer/multiple-choice-answer-detail.html',
                    controller: 'MultipleChoiceAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('multipleChoiceAnswer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MultipleChoiceAnswer', function($stateParams, MultipleChoiceAnswer) {
                    return MultipleChoiceAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'multiple-choice-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('multiple-choice-answer-detail.edit', {
            parent: 'multiple-choice-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-answer/multiple-choice-answer-dialog.html',
                    controller: 'MultipleChoiceAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MultipleChoiceAnswer', function(MultipleChoiceAnswer) {
                            return MultipleChoiceAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('multiple-choice-answer.new', {
            parent: 'multiple-choice-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-answer/multiple-choice-answer-dialog.html',
                    controller: 'MultipleChoiceAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('multiple-choice-answer', null, { reload: true });
                }, function() {
                    $state.go('multiple-choice-answer');
                });
            }]
        })
        .state('multiple-choice-answer.edit', {
            parent: 'multiple-choice-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-answer/multiple-choice-answer-dialog.html',
                    controller: 'MultipleChoiceAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MultipleChoiceAnswer', function(MultipleChoiceAnswer) {
                            return MultipleChoiceAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('multiple-choice-answer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('multiple-choice-answer.delete', {
            parent: 'multiple-choice-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-answer/multiple-choice-answer-delete-dialog.html',
                    controller: 'MultipleChoiceAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MultipleChoiceAnswer', function(MultipleChoiceAnswer) {
                            return MultipleChoiceAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('multiple-choice-answer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
