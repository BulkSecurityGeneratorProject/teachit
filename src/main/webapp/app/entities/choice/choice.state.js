(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('choice', {
            parent: 'entity',
            url: '/choice',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.choice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/choice/choices.html',
                    controller: 'ChoiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('choice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('choice-detail', {
            parent: 'entity',
            url: '/choice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.choice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/choice/choice-detail.html',
                    controller: 'ChoiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('choice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Choice', function($stateParams, Choice) {
                    return Choice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'choice',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('choice-detail.edit', {
            parent: 'choice-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice/choice-dialog.html',
                    controller: 'ChoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Choice', function(Choice) {
                            return Choice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('choice.new', {
            parent: 'choice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice/choice-dialog.html',
                    controller: 'ChoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                response: null,
                                correct: null,
                                ordering: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('choice', null, { reload: true });
                }, function() {
                    $state.go('choice');
                });
            }]
        })
        .state('choice.edit', {
            parent: 'choice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice/choice-dialog.html',
                    controller: 'ChoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Choice', function(Choice) {
                            return Choice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('choice', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('choice.delete', {
            parent: 'choice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice/choice-delete-dialog.html',
                    controller: 'ChoiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Choice', function(Choice) {
                            return Choice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('choice', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
