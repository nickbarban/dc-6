(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .factory('LessonSearch', LessonSearch);

    LessonSearch.$inject = ['$resource'];

    function LessonSearch($resource) {
        var resourceUrl =  'api/_search/lessons/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
