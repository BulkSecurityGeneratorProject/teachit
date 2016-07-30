(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('DiscursiveAnswerDialogController', DiscursiveAnswerDialogController);

    DiscursiveAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DiscursiveAnswer', 'DiscursiveQuestion', 'Person'];

    function DiscursiveAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DiscursiveAnswer, DiscursiveQuestion, Person) {
        var vm = this;

        vm.discursiveAnswer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.discursivequestions = DiscursiveQuestion.query();
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.discursiveAnswer.id !== null) {
                DiscursiveAnswer.update(vm.discursiveAnswer, onSaveSuccess, onSaveError);
            } else {
                DiscursiveAnswer.save(vm.discursiveAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:discursiveAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
