'use strict';

describe('Controller Tests', function() {

    describe('MultipleChoiceAnswer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMultipleChoiceAnswer, MockChoice, MockMultipleChoiceQuestion, MockPerson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMultipleChoiceAnswer = jasmine.createSpy('MockMultipleChoiceAnswer');
            MockChoice = jasmine.createSpy('MockChoice');
            MockMultipleChoiceQuestion = jasmine.createSpy('MockMultipleChoiceQuestion');
            MockPerson = jasmine.createSpy('MockPerson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MultipleChoiceAnswer': MockMultipleChoiceAnswer,
                'Choice': MockChoice,
                'MultipleChoiceQuestion': MockMultipleChoiceQuestion,
                'Person': MockPerson
            };
            createController = function() {
                $injector.get('$controller')("MultipleChoiceAnswerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:multipleChoiceAnswerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
