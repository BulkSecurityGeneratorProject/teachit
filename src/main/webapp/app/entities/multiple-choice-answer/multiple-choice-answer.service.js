(function() {
    'use strict';
    angular
        .module('teachitApp')
        .factory('MultipleChoiceAnswer', MultipleChoiceAnswer);

    MultipleChoiceAnswer.$inject = ['$resource'];

    function MultipleChoiceAnswer ($resource) {
        var resourceUrl =  'api/multiple-choice-answers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
