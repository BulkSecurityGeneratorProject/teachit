(function() {
    'use strict';
    angular
        .module('teachitApp')
        .factory('DiscursiveAnswer', DiscursiveAnswer);

    DiscursiveAnswer.$inject = ['$resource'];

    function DiscursiveAnswer ($resource) {
        var resourceUrl =  'api/discursive-answers/:id';

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
