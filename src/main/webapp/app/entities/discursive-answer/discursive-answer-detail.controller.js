(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('DiscursiveAnswerDetailController', DiscursiveAnswerDetailController);

    DiscursiveAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DiscursiveAnswer', 'DiscursiveQuestion', 'Person'];

    function DiscursiveAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, DiscursiveAnswer, DiscursiveQuestion, Person) {
        var vm = this;

        vm.discursiveAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:discursiveAnswerUpdate', function(event, result) {
            vm.discursiveAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
