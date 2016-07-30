(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('content-view', {
            parent: 'entity',
            url: '/content-view',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.contentView.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content-view/content-views.html',
                    controller: 'ContentViewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contentView');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('content-view-detail', {
            parent: 'entity',
            url: '/content-view/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.contentView.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content-view/content-view-detail.html',
                    controller: 'ContentViewDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contentView');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContentView', function($stateParams, ContentView) {
                    return ContentView.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'content-view',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('content-view-detail.edit', {
            parent: 'content-view-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-view/content-view-dialog.html',
                    controller: 'ContentViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContentView', function(ContentView) {
                            return ContentView.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content-view.new', {
            parent: 'content-view',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-view/content-view-dialog.html',
                    controller: 'ContentViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                view: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('content-view', null, { reload: true });
                }, function() {
                    $state.go('content-view');
                });
            }]
        })
        .state('content-view.edit', {
            parent: 'content-view',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-view/content-view-dialog.html',
                    controller: 'ContentViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContentView', function(ContentView) {
                            return ContentView.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content-view', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content-view.delete', {
            parent: 'content-view',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-view/content-view-delete-dialog.html',
                    controller: 'ContentViewDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContentView', function(ContentView) {
                            return ContentView.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content-view', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
