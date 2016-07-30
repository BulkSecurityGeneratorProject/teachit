(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('ContentDetailController', ContentDetailController);

    ContentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Content', 'Course', 'Lesson'];

    function ContentDetailController($scope, $rootScope, $stateParams, previousState, entity, Content, Course, Lesson) {
        var vm = this;

        vm.content = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('teachitApp:contentUpdate', function(event, result) {
            vm.content = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
