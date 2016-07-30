(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('content', {
            parent: 'entity',
            url: '/content',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.content.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content/contents.html',
                    controller: 'ContentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('content');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('content-detail', {
            parent: 'entity',
            url: '/content/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.content.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content/content-detail.html',
                    controller: 'ContentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('content');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Content', function($stateParams, Content) {
                    return Content.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'content',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('content-detail.edit', {
            parent: 'content-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content/content-dialog.html',
                    controller: 'ContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Content', function(Content) {
                            return Content.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content.new', {
            parent: 'content',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content/content-dialog.html',
                    controller: 'ContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fileName: null,
                                startDate: null,
                                description: null,
                                text: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('content', null, { reload: true });
                }, function() {
                    $state.go('content');
                });
            }]
        })
        .state('content.edit', {
            parent: 'content',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content/content-dialog.html',
                    controller: 'ContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Content', function(Content) {
                            return Content.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content.delete', {
            parent: 'content',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content/content-delete-dialog.html',
                    controller: 'ContentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Content', function(Content) {
                            return Content.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
