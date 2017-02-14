(function() {
    'use strict';

    angular
        .module('dancekvartalApp')
        .controller('LessonMySuffixDeleteController',LessonMySuffixDeleteController);

    LessonMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Lesson'];

    function LessonMySuffixDeleteController($uibModalInstance, entity, Lesson) {
        var vm = this;

        vm.lesson = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Lesson.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
