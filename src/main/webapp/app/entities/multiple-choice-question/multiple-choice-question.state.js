(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('multiple-choice-question', {
            parent: 'entity',
            url: '/multiple-choice-question',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.multipleChoiceQuestion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/multiple-choice-question/multiple-choice-questions.html',
                    controller: 'MultipleChoiceQuestionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('multipleChoiceQuestion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('multiple-choice-question-detail', {
            parent: 'entity',
            url: '/multiple-choice-question/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.multipleChoiceQuestion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/multiple-choice-question/multiple-choice-question-detail.html',
                    controller: 'MultipleChoiceQuestionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('multipleChoiceQuestion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MultipleChoiceQuestion', function($stateParams, MultipleChoiceQuestion) {
                    return MultipleChoiceQuestion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'multiple-choice-question',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('multiple-choice-question-detail.edit', {
            parent: 'multiple-choice-question-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-question/multiple-choice-question-dialog.html',
                    controller: 'MultipleChoiceQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MultipleChoiceQuestion', function(MultipleChoiceQuestion) {
                            return MultipleChoiceQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('multiple-choice-question.new', {
            parent: 'multiple-choice-question',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-question/multiple-choice-question-dialog.html',
                    controller: 'MultipleChoiceQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ordering: null,
                                startDate: null,
                                question: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('multiple-choice-question', null, { reload: true });
                }, function() {
                    $state.go('multiple-choice-question');
                });
            }]
        })
        .state('multiple-choice-question.edit', {
            parent: 'multiple-choice-question',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-question/multiple-choice-question-dialog.html',
                    controller: 'MultipleChoiceQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MultipleChoiceQuestion', function(MultipleChoiceQuestion) {
                            return MultipleChoiceQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('multiple-choice-question', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('multiple-choice-question.delete', {
            parent: 'multiple-choice-question',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/multiple-choice-question/multiple-choice-question-delete-dialog.html',
                    controller: 'MultipleChoiceQuestionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MultipleChoiceQuestion', function(MultipleChoiceQuestion) {
                            return MultipleChoiceQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('multiple-choice-question', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
