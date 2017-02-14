(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .factory('SubjectSearch', SubjectSearch);

    SubjectSearch.$inject = ['$resource'];

    function SubjectSearch($resource) {
        var resourceUrl =  'api/_search/subjects/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
