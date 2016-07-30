(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('MultipleChoiceQuestionDetailController', MultipleChoiceQuestionDetailController);

    MultipleChoiceQuestionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MultipleChoiceQuestion', 'Choice', 'Course', 'Lesson'];

    function MultipleChoiceQuestionDetailController($scope, $rootScope, $stateParams, previousState, entity, MultipleChoiceQuestion, Choice, Course, Lesson) {
        var vm = this;

        vm.multipleChoiceQuestion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:multipleChoiceQuestionUpdate', function(event, result) {
            vm.multipleChoiceQuestion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
