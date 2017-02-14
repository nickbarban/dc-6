(function() {
    'use strict';
    angular
        .module('dancekvartalApp')
        .factory('Teacher', Teacher);

    Teacher.$inject = ['$resource', 'DateUtils'];

    function Teacher ($resource, DateUtils) {
        var resourceUrl =  'api/teachers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthday = DateUtils.convertLocalDateFromServer(data.birthday);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.birthday = DateUtils.convertLocalDateToServer(copy.birthday);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.birthday = DateUtils.convertLocalDateToServer(copy.birthday);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
