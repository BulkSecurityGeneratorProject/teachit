(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ChoiceDeleteController',ChoiceDeleteController);

    ChoiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Choice'];

    function ChoiceDeleteController($uibModalInstance, entity, Choice) {
        var vm = this;

        vm.choice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Choice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
