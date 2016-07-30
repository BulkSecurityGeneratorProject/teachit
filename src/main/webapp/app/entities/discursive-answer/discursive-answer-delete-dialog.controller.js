(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('DiscursiveAnswerDeleteController',DiscursiveAnswerDeleteController);

    DiscursiveAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'DiscursiveAnswer'];

    function DiscursiveAnswerDeleteController($uibModalInstance, entity, DiscursiveAnswer) {
        var vm = this;

        vm.discursiveAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DiscursiveAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
