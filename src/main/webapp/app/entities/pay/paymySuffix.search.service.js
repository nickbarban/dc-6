(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .factory('PaySearch', PaySearch);

    PaySearch.$inject = ['$resource'];

    function PaySearch($resource) {
        var resourceUrl =  'api/_search/pays/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
