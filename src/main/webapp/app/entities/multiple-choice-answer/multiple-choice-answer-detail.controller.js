(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('MultipleChoiceAnswerDetailController', MultipleChoiceAnswerDetailController);

    MultipleChoiceAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MultipleChoiceAnswer', 'Choice', 'MultipleChoiceQuestion', 'Person'];

    function MultipleChoiceAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, MultipleChoiceAnswer, Choice, MultipleChoiceQuestion, Person) {
        var vm = this;

        vm.multipleChoiceAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:multipleChoiceAnswerUpdate', function(event, result) {
            vm.multipleChoiceAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
