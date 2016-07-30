(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ContentDialogController', ContentDialogController);

    ContentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Content', 'Course', 'Lesson'];

    function ContentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Content, Course, Lesson) {
        var vm = this;

        vm.content = entity;
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
            if (vm.content.id !== null) {
                Content.update(vm.content, onSaveSuccess, onSaveError);
            } else {
                Content.save(vm.content, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:contentUpdate', result);
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
