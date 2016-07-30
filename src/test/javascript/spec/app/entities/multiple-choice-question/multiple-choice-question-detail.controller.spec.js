'use strict';

describe('Controller Tests', function() {

    describe('MultipleChoiceQuestion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMultipleChoiceQuestion, MockChoice, MockCourse, MockLesson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMultipleChoiceQuestion = jasmine.createSpy('MockMultipleChoiceQuestion');
            MockChoice = jasmine.createSpy('MockChoice');
            MockCourse = jasmine.createSpy('MockCourse');
            MockLesson = jasmine.createSpy('MockLesson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MultipleChoiceQuestion': MockMultipleChoiceQuestion,
                'Choice': MockChoice,
                'Course': MockCourse,
                'Lesson': MockLesson
            };
            createController = function() {
                $injector.get('$controller')("MultipleChoiceQuestionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:multipleChoiceQuestionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
