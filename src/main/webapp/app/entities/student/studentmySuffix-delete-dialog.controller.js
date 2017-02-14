(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('StudentMySuffixDeleteController',StudentMySuffixDeleteController);

    StudentMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Student'];

    function StudentMySuffixDeleteController($uibModalInstance, entity, Student) {
        var vm = this;

        vm.student = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Student.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
