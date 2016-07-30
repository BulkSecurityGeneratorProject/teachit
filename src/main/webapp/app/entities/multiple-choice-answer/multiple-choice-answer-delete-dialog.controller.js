(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('MultipleChoiceAnswerDeleteController',MultipleChoiceAnswerDeleteController);

    MultipleChoiceAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'MultipleChoiceAnswer'];

    function MultipleChoiceAnswerDeleteController($uibModalInstance, entity, MultipleChoiceAnswer) {
        var vm = this;

        vm.multipleChoiceAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MultipleChoiceAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
