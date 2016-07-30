(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'ApplicationAdmission', 'ContentView', 'Course'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, ApplicationAdmission, ContentView, Course) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
