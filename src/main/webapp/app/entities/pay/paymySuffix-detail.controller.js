(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('PayMySuffixDetailController', PayMySuffixDetailController);

    PayMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pay'];

    function PayMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Pay) {
        var vm = this;

        vm.pay = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dancekvartalApp:payUpdate', function(event, result) {
            vm.pay = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
