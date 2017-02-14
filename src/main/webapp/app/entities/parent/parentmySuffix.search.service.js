(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .factory('ParentSearch', ParentSearch);

    ParentSearch.$inject = ['$resource'];

    function ParentSearch($resource) {
        var resourceUrl =  'api/_search/parents/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
