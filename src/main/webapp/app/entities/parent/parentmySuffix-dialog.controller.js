(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('ParentMySuffixDialogController', ParentMySuffixDialogController);

    ParentMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Parent', 'Student'];

    function ParentMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Parent, Student) {
        var vm = this;

        vm.parent = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.parent.id !== null) {
                Parent.update(vm.parent, onSaveSuccess, onSaveError);
            } else {
                Parent.save(vm.parent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dancekvartalApp:parentUpdate', result);
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
