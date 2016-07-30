(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('DiscursiveAnswerController', DiscursiveAnswerController);

    DiscursiveAnswerController.$inject = ['$scope', '$state', 'DiscursiveAnswer', 'ParseLinks', 'AlertService'];

    function DiscursiveAnswerController ($scope, $state, DiscursiveAnswer, ParseLinks, AlertService) {
        var vm = this;
        
        vm.discursiveAnswers = [];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        function loadAll () {
            DiscursiveAnswer.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.discursiveAnswers.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.discursiveAnswers = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
