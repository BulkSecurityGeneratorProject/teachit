(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('LessonDialogController', LessonDialogController);

    LessonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lesson', 'MultipleChoiceQuestion', 'DiscursiveQuestion', 'Content', 'Course'];

    function LessonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Lesson, MultipleChoiceQuestion, DiscursiveQuestion, Content, Course) {
        var vm = this;

        vm.lesson = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.multiplechoicequestions = MultipleChoiceQuestion.query();
        vm.discursivequestions = DiscursiveQuestion.query();
        vm.contents = Content.query();
        vm.courses = Course.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lesson.id !== null) {
                Lesson.update(vm.lesson, onSaveSuccess, onSaveError);
            } else {
                Lesson.save(vm.lesson, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:lessonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
