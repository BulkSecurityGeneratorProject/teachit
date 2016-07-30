(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('LessonDetailController', LessonDetailController);

    LessonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Lesson', 'MultipleChoiceQuestion', 'DiscursiveQuestion', 'Content', 'Course'];

    function LessonDetailController($scope, $rootScope, $stateParams, previousState, entity, Lesson, MultipleChoiceQuestion, DiscursiveQuestion, Content, Course) {
        var vm = this;

        vm.lesson = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:lessonUpdate', function(event, result) {
            vm.lesson = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
