'use strict';

describe('Controller Tests', function() {

    describe('Lesson Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLesson, MockSubject, MockStudent, MockTeacher;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLesson = jasmine.createSpy('MockLesson');
            MockSubject = jasmine.createSpy('MockSubject');
            MockStudent = jasmine.createSpy('MockStudent');
            MockTeacher = jasmine.createSpy('MockTeacher');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Lesson': MockLesson,
                'Subject': MockSubject,
                'Student': MockStudent,
                'Teacher': MockTeacher
            };
            createController = function() {
                $injector.get('$controller')("LessonMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'dancekvartalApp:lessonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
