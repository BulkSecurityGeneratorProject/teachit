(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('MultipleChoiceQuestionDialogController', MultipleChoiceQuestionDialogController);

    MultipleChoiceQuestionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MultipleChoiceQuestion', 'Choice', 'Course', 'Lesson'];

    function MultipleChoiceQuestionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MultipleChoiceQuestion, Choice, Course, Lesson) {
        var vm = this;

        vm.multipleChoiceQuestion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.choices = Choice.query();
        vm.courses = Course.query();
        vm.lessons = Lesson.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.multipleChoiceQuestion.id !== null) {
                MultipleChoiceQuestion.update(vm.multipleChoiceQuestion, onSaveSuccess, onSaveError);
            } else {
                MultipleChoiceQuestion.save(vm.multipleChoiceQuestion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:multipleChoiceQuestionUpdate', result);
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
