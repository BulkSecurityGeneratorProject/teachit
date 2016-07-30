(function() {
    'use strict';

    angular
        .module('teachitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('application-admission', {
            parent: 'entity',
            url: '/application-admission',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.applicationAdmission.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/application-admission/application-admissions.html',
                    controller: 'ApplicationAdmissionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('applicationAdmission');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('application-admission-detail', {
            parent: 'entity',
            url: '/application-admission/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'teachitApp.applicationAdmission.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/application-admission/application-admission-detail.html',
                    controller: 'ApplicationAdmissionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('applicationAdmission');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ApplicationAdmission', function($stateParams, ApplicationAdmission) {
                    return ApplicationAdmission.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'application-admission',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('application-admission-detail.edit', {
            parent: 'application-admission-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-admission/application-admission-dialog.html',
                    controller: 'ApplicationAdmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApplicationAdmission', function(ApplicationAdmission) {
                            return ApplicationAdmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('application-admission.new', {
            parent: 'application-admission',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-admission/application-admission-dialog.html',
                    controller: 'ApplicationAdmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                requestDate: null,
                                accepted: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('application-admission', null, { reload: true });
                }, function() {
                    $state.go('application-admission');
                });
            }]
        })
        .state('application-admission.edit', {
            parent: 'application-admission',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-admission/application-admission-dialog.html',
                    controller: 'ApplicationAdmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApplicationAdmission', function(ApplicationAdmission) {
                            return ApplicationAdmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('application-admission', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('application-admission.delete', {
            parent: 'application-admission',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-admission/application-admission-delete-dialog.html',
                    controller: 'ApplicationAdmissionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ApplicationAdmission', function(ApplicationAdmission) {
                            return ApplicationAdmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('application-admission', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
