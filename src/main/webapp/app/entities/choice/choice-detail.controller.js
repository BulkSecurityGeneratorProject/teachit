(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ChoiceDetailController', ChoiceDetailController);

    ChoiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Choice', 'MultipleChoiceQuestion'];

    function ChoiceDetailController($scope, $rootScope, $stateParams, previousState, entity, Choice, MultipleChoiceQuestion) {
        var vm = this;

        vm.choice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:choiceUpdate', function(event, result) {
            vm.choice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
