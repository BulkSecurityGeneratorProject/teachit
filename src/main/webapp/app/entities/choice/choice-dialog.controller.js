(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ChoiceDialogController', ChoiceDialogController);

    ChoiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Choice', 'MultipleChoiceQuestion'];

    function ChoiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Choice, MultipleChoiceQuestion) {
        var vm = this;

        vm.choice = entity;
        vm.clear = clear;
        vm.save = save;
        vm.multiplechoicequestions = MultipleChoiceQuestion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.choice.id !== null) {
                Choice.update(vm.choice, onSaveSuccess, onSaveError);
            } else {
                Choice.save(vm.choice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:choiceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
