(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ApplicationAdmissionDeleteController',ApplicationAdmissionDeleteController);

    ApplicationAdmissionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ApplicationAdmission'];

    function ApplicationAdmissionDeleteController($uibModalInstance, entity, ApplicationAdmission) {
        var vm = this;

        vm.applicationAdmission = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ApplicationAdmission.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
