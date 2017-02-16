(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('LessonMySuffixDetailController', LessonMySuffixDetailController);

    LessonMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Lesson', 'Subject', 'Student', 'Teacher'];

    function LessonMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Lesson, Subject, Student, Teacher) {
        var vm = this;

        vm.lesson = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dancekvartalApp:lessonUpdate', function(event, result) {
            vm.lesson = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
