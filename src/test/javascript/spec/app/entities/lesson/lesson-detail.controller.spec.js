'use strict';

describe('Controller Tests', function() {

    describe('Lesson Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLesson, MockMultipleChoiceQuestion, MockDiscursiveQuestion, MockContent, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLesson = jasmine.createSpy('MockLesson');
            MockMultipleChoiceQuestion = jasmine.createSpy('MockMultipleChoiceQuestion');
            MockDiscursiveQuestion = jasmine.createSpy('MockDiscursiveQuestion');
            MockContent = jasmine.createSpy('MockContent');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Lesson': MockLesson,
                'MultipleChoiceQuestion': MockMultipleChoiceQuestion,
                'DiscursiveQuestion': MockDiscursiveQuestion,
                'Content': MockContent,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("LessonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:lessonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
