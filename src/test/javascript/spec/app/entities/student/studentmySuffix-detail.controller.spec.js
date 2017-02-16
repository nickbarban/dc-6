'use strict';

describe('Controller Tests', function() {

    describe('Student Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudent, MockSubject, MockPay, MockLesson, MockParent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudent = jasmine.createSpy('MockStudent');
            MockSubject = jasmine.createSpy('MockSubject');
            MockPay = jasmine.createSpy('MockPay');
            MockLesson = jasmine.createSpy('MockLesson');
            MockParent = jasmine.createSpy('MockParent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Student': MockStudent,
                'Subject': MockSubject,
                'Pay': MockPay,
                'Lesson': MockLesson,
                'Parent': MockParent
            };
            createController = function() {
                $injector.get('$controller')("StudentMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'dancekvartalApp:studentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
