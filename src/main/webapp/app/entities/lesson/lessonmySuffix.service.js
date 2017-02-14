(function() {
    'use strict';
    angular
        .module('dancekvartalApp')
        .factory('Lesson', Lesson);

    Lesson.$inject = ['$resource', 'DateUtils'];

    function Lesson ($resource, DateUtils) {
        var resourceUrl =  'api/lessons/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startLesson = DateUtils.convertDateTimeFromServer(data.startLesson);
                        data.endLesson = DateUtils.convertDateTimeFromServer(data.endLesson);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
