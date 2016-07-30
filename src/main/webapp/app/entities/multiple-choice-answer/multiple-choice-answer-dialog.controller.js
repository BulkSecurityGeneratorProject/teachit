(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('MultipleChoiceAnswerDialogController', MultipleChoiceAnswerDialogController);

    MultipleChoiceAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MultipleChoiceAnswer', 'Choice', 'MultipleChoiceQuestion', 'Person'];

    function MultipleChoiceAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MultipleChoiceAnswer, Choice, MultipleChoiceQuestion, Person) {
        var vm = this;

        vm.multipleChoiceAnswer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.choosens = Choice.query({filter: 'multiplechoiceanswer-is-null'});
        $q.all([vm.multipleChoiceAnswer.$promise, vm.choosens.$promise]).then(function() {
            if (!vm.multipleChoiceAnswer.choosen || !vm.multipleChoiceAnswer.choosen.id) {
                return $q.reject();
            }
            return Choice.get({id : vm.multipleChoiceAnswer.choosen.id}).$promise;
        }).then(function(choosen) {
            vm.choosens.push(choosen);
        });
        vm.multiplechoicequestions = MultipleChoiceQuestion.query();
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.multipleChoiceAnswer.id !== null) {
                MultipleChoiceAnswer.update(vm.multipleChoiceAnswer, onSaveSuccess, onSaveError);
            } else {
                MultipleChoiceAnswer.save(vm.multipleChoiceAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:multipleChoiceAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
