(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ContentViewDetailController', ContentViewDetailController);

    ContentViewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContentView', 'Person', 'Content'];

    function ContentViewDetailController($scope, $rootScope, $stateParams, previousState, entity, ContentView, Person, Content) {
        var vm = this;

        vm.contentView = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:contentViewUpdate', function(event, result) {
            vm.contentView = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
