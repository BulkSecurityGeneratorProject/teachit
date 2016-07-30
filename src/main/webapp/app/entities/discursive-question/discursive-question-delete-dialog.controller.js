(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('DiscursiveQuestionDeleteController',DiscursiveQuestionDeleteController);

    DiscursiveQuestionDeleteController.$inject = ['$uibModalInstance', 'entity', 'DiscursiveQuestion'];

    function DiscursiveQuestionDeleteController($uibModalInstance, entity, DiscursiveQuestion) {
        var vm = this;

        vm.discursiveQuestion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DiscursiveQuestion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
