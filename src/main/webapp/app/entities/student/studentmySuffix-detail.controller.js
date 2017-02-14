(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('StudentMySuffixDetailController', StudentMySuffixDetailController);

    StudentMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student'];

    function StudentMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Student) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dancekvartalApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
