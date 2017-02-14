(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('SubjectMySuffixDetailController', SubjectMySuffixDetailController);

    SubjectMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Subject'];

    function SubjectMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Subject) {
        var vm = this;

        vm.subject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dancekvartalApp:subjectUpdate', function(event, result) {
            vm.subject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
