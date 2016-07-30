(function() {
    'use strict';
    angular
        .module('teachitApp')
        .factory('ApplicationAdmission', ApplicationAdmission);

    ApplicationAdmission.$inject = ['$resource', 'DateUtils'];

    function ApplicationAdmission ($resource, DateUtils) {
        var resourceUrl =  'api/application-admissions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.requestDate = DateUtils.convertLocalDateFromServer(data.requestDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.requestDate = DateUtils.convertLocalDateToServer(data.requestDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.requestDate = DateUtils.convertLocalDateToServer(data.requestDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
