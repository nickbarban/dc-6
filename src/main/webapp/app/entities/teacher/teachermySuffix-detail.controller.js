(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('TeacherMySuffixDetailController', TeacherMySuffixDetailController);

    TeacherMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Teacher', 'Subject', 'Lesson', 'Pay'];

    function TeacherMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Teacher, Subject, Lesson, Pay) {
        var vm = this;

        vm.teacher = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dancekvartalApp:teacherUpdate', function(event, result) {
            vm.teacher = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
