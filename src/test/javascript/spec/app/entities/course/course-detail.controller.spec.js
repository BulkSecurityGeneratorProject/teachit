'use strict';

describe('Controller Tests', function() {

    describe('Course Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCourse, MockApplicationAdmission, MockMultipleChoiceQuestion, MockDiscursiveQuestion, MockContent, MockLesson, MockPerson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCourse = jasmine.createSpy('MockCourse');
            MockApplicationAdmission = jasmine.createSpy('MockApplicationAdmission');
            MockMultipleChoiceQuestion = jasmine.createSpy('MockMultipleChoiceQuestion');
            MockDiscursiveQuestion = jasmine.createSpy('MockDiscursiveQuestion');
            MockContent = jasmine.createSpy('MockContent');
            MockLesson = jasmine.createSpy('MockLesson');
            MockPerson = jasmine.createSpy('MockPerson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Course': MockCourse,
                'ApplicationAdmission': MockApplicationAdmission,
                'MultipleChoiceQuestion': MockMultipleChoiceQuestion,
                'DiscursiveQuestion': MockDiscursiveQuestion,
                'Content': MockContent,
                'Lesson': MockLesson,
                'Person': MockPerson
            };
            createController = function() {
                $injector.get('$controller')("CourseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:courseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
