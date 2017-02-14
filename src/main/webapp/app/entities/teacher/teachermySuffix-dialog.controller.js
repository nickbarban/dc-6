(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('TeacherMySuffixDialogController', TeacherMySuffixDialogController);

    TeacherMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Teacher'];

    function TeacherMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Teacher) {
        var vm = this;

        vm.teacher = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.teacher.id !== null) {
                Teacher.update(vm.teacher, onSaveSuccess, onSaveError);
            } else {
                Teacher.save(vm.teacher, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dancekvartalApp:teacherUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthday = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
