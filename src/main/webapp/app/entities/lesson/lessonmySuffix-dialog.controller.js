(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('LessonMySuffixDialogController', LessonMySuffixDialogController);

    LessonMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lesson', 'Subject', 'Student', 'Teacher'];

    function LessonMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Lesson, Subject, Student, Teacher) {
        var vm = this;

        vm.lesson = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.subjects = Subject.query();
        vm.students = Student.query();
        vm.teachers = Teacher.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lesson.id !== null) {
                Lesson.update(vm.lesson, onSaveSuccess, onSaveError);
            } else {
                Lesson.save(vm.lesson, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dancekvartalApp:lessonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startLesson = false;
        vm.datePickerOpenStatus.endLesson = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
