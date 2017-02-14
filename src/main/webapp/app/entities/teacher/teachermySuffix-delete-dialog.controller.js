(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('TeacherMySuffixDeleteController',TeacherMySuffixDeleteController);

    TeacherMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Teacher'];

    function TeacherMySuffixDeleteController($uibModalInstance, entity, Teacher) {
        var vm = this;

        vm.teacher = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Teacher.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
