(function() {
    'use strict';
    angular
        .module('teachitApp')
        .factory('ContentView', ContentView);

    ContentView.$inject = ['$resource'];

    function ContentView ($resource) {
        var resourceUrl =  'api/content-views/:id';

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
