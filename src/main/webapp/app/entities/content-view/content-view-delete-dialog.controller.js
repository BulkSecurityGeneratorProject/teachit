(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ContentViewDeleteController',ContentViewDeleteController);

    ContentViewDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContentView'];

    function ContentViewDeleteController($uibModalInstance, entity, ContentView) {
        var vm = this;

        vm.contentView = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContentView.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
