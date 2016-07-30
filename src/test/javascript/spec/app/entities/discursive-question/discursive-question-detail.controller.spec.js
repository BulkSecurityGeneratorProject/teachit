'use strict';

describe('Controller Tests', function() {

    describe('DiscursiveQuestion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDiscursiveQuestion, MockCourse, MockLesson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDiscursiveQuestion = jasmine.createSpy('MockDiscursiveQuestion');
            MockCourse = jasmine.createSpy('MockCourse');
            MockLesson = jasmine.createSpy('MockLesson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DiscursiveQuestion': MockDiscursiveQuestion,
                'Course': MockCourse,
                'Lesson': MockLesson
            };
            createController = function() {
                $injector.get('$controller')("DiscursiveQuestionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:discursiveQuestionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
