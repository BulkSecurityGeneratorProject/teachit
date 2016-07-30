(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('discursive-question', {
            parent: 'entity',
            url: '/discursive-question',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.discursiveQuestion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discursive-question/discursive-questions.html',
                    controller: 'DiscursiveQuestionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discursiveQuestion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('discursive-question-detail', {
            parent: 'entity',
            url: '/discursive-question/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.discursiveQuestion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discursive-question/discursive-question-detail.html',
                    controller: 'DiscursiveQuestionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discursiveQuestion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DiscursiveQuestion', function($stateParams, DiscursiveQuestion) {
                    return DiscursiveQuestion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'discursive-question',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('discursive-question-detail.edit', {
            parent: 'discursive-question-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-question/discursive-question-dialog.html',
                    controller: 'DiscursiveQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiscursiveQuestion', function(DiscursiveQuestion) {
                            return DiscursiveQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discursive-question.new', {
            parent: 'discursive-question',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-question/discursive-question-dialog.html',
                    controller: 'DiscursiveQuestionDialogController',
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
                    $state.go('discursive-question', null, { reload: true });
                }, function() {
                    $state.go('discursive-question');
                });
            }]
        })
        .state('discursive-question.edit', {
            parent: 'discursive-question',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-question/discursive-question-dialog.html',
                    controller: 'DiscursiveQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiscursiveQuestion', function(DiscursiveQuestion) {
                            return DiscursiveQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discursive-question', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discursive-question.delete', {
            parent: 'discursive-question',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discursive-question/discursive-question-delete-dialog.html',
                    controller: 'DiscursiveQuestionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DiscursiveQuestion', function(DiscursiveQuestion) {
                            return DiscursiveQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discursive-question', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
