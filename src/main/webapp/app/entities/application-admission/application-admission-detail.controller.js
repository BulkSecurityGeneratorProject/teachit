(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ApplicationAdmissionDetailController', ApplicationAdmissionDetailController);

    ApplicationAdmissionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ApplicationAdmission', 'Person', 'Course'];

    function ApplicationAdmissionDetailController($scope, $rootScope, $stateParams, previousState, entity, ApplicationAdmission, Person, Course) {
        var vm = this;

        vm.applicationAdmission = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:applicationAdmissionUpdate', function(event, result) {
            vm.applicationAdmission = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
