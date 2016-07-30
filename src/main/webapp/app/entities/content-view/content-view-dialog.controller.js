(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ContentViewDialogController', ContentViewDialogController);

    ContentViewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContentView', 'Person', 'Content'];

    function ContentViewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContentView, Person, Content) {
        var vm = this;

        vm.contentView = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query();
        vm.contents = Content.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contentView.id !== null) {
                ContentView.update(vm.contentView, onSaveSuccess, onSaveError);
            } else {
                ContentView.save(vm.contentView, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:contentViewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
