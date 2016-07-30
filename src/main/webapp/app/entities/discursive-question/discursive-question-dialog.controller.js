(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('DiscursiveQuestionDialogController', DiscursiveQuestionDialogController);

    DiscursiveQuestionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DiscursiveQuestion', 'Course', 'Lesson'];

    function DiscursiveQuestionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DiscursiveQuestion, Course, Lesson) {
        var vm = this;

        vm.discursiveQuestion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
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
            if (vm.discursiveQuestion.id !== null) {
                DiscursiveQuestion.update(vm.discursiveQuestion, onSaveSuccess, onSaveError);
            } else {
                DiscursiveQuestion.save(vm.discursiveQuestion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:discursiveQuestionUpdate', result);
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
