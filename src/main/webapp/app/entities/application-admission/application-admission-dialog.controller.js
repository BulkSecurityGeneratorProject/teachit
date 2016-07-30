(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ApplicationAdmissionDialogController', ApplicationAdmissionDialogController);

    ApplicationAdmissionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApplicationAdmission', 'Person', 'Course'];

    function ApplicationAdmissionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ApplicationAdmission, Person, Course) {
        var vm = this;

        vm.applicationAdmission = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.people = Person.query();
        vm.courses = Course.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.applicationAdmission.id !== null) {
                ApplicationAdmission.update(vm.applicationAdmission, onSaveSuccess, onSaveError);
            } else {
                ApplicationAdmission.save(vm.applicationAdmission, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:applicationAdmissionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.requestDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
