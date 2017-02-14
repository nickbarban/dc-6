(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('ParentMySuffixDeleteController',ParentMySuffixDeleteController);

    ParentMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Parent'];

    function ParentMySuffixDeleteController($uibModalInstance, entity, Parent) {
        var vm = this;

        vm.parent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Parent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
