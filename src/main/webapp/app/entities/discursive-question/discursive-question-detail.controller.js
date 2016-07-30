(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('DiscursiveQuestionDetailController', DiscursiveQuestionDetailController);

    DiscursiveQuestionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DiscursiveQuestion', 'Course', 'Lesson'];

    function DiscursiveQuestionDetailController($scope, $rootScope, $stateParams, previousState, entity, DiscursiveQuestion, Course, Lesson) {
        var vm = this;

        vm.discursiveQuestion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:discursiveQuestionUpdate', function(event, result) {
            vm.discursiveQuestion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
