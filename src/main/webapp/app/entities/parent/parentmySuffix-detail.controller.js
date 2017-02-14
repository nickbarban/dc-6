(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('ParentMySuffixDetailController', ParentMySuffixDetailController);

    ParentMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Parent'];

    function ParentMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Parent) {
        var vm = this;

        vm.parent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dancekvartalApp:parentUpdate', function(event, result) {
            vm.parent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
