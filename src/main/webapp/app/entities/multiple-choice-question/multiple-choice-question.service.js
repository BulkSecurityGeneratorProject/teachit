(function() {
    'use strict';
    angular
        .module('teachitApp')
        .factory('MultipleChoiceQuestion', MultipleChoiceQuestion);

    MultipleChoiceQuestion.$inject = ['$resource', 'DateUtils'];

    function MultipleChoiceQuestion ($resource, DateUtils) {
        var resourceUrl =  'api/multiple-choice-questions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
