(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('PersonDialogController', PersonDialogController);

    PersonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Person', 'ApplicationAdmission', 'ContentView', 'Course'];

    function PersonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Person, ApplicationAdmission, ContentView, Course) {
        var vm = this;

        vm.person = entity;
        vm.clear = clear;
        vm.save = save;
        vm.applicationadmissions = ApplicationAdmission.query({filter: 'candidate-is-null'});
        $q.all([vm.person.$promise, vm.applicationadmissions.$promise]).then(function() {
            if (!vm.person.applicationAdmission || !vm.person.applicationAdmission.id) {
                return $q.reject();
            }
            return ApplicationAdmission.get({id : vm.person.applicationAdmission.id}).$promise;
        }).then(function(applicationAdmission) {
            vm.applicationadmissions.push(applicationAdmission);
        });
        vm.contentviews = ContentView.query();
        vm.courses = Course.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.person.id !== null) {
                Person.update(vm.person, onSaveSuccess, onSaveError);
            } else {
                Person.save(vm.person, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:personUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
