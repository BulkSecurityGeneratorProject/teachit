'use strict';

describe('Controller Tests', function() {

    describe('Choice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChoice, MockMultipleChoiceQuestion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChoice = jasmine.createSpy('MockChoice');
            MockMultipleChoiceQuestion = jasmine.createSpy('MockMultipleChoiceQuestion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Choice': MockChoice,
                'MultipleChoiceQuestion': MockMultipleChoiceQuestion
            };
            createController = function() {
                $injector.get('$controller')("ChoiceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:choiceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
