(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('CourseDetailController', CourseDetailController);

    CourseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Course', 'ApplicationAdmission', 'MultipleChoiceQuestion', 'DiscursiveQuestion', 'Content', 'Lesson', 'Person'];

    function CourseDetailController($scope, $rootScope, $stateParams, previousState, entity, Course, ApplicationAdmission, MultipleChoiceQuestion, DiscursiveQuestion, Content, Lesson, Person) {
        var vm = this;

        vm.course = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:courseUpdate', function(event, result) {
            vm.course = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
