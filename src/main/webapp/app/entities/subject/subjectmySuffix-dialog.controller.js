(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('SubjectMySuffixDialogController', SubjectMySuffixDialogController);

    SubjectMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Subject', 'Teacher', 'Student'];

    function SubjectMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Subject, Teacher, Student) {
        var vm = this;

        vm.subject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.teachers = Teacher.query();
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subject.id !== null) {
                Subject.update(vm.subject, onSaveSuccess, onSaveError);
            } else {
                Subject.save(vm.subject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dancekvartalApp:subjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
