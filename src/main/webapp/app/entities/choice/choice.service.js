(function() {
    'use strict';
    angular
        .module('teachitApp')
        .factory('Choice', Choice);

    Choice.$inject = ['$resource'];

    function Choice ($resource) {
        var resourceUrl =  'api/choices/:id';

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
