'use strict';

describe('Controller Tests', function() {

    describe('DiscursiveAnswer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDiscursiveAnswer, MockDiscursiveQuestion, MockPerson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDiscursiveAnswer = jasmine.createSpy('MockDiscursiveAnswer');
            MockDiscursiveQuestion = jasmine.createSpy('MockDiscursiveQuestion');
            MockPerson = jasmine.createSpy('MockPerson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DiscursiveAnswer': MockDiscursiveAnswer,
                'DiscursiveQuestion': MockDiscursiveQuestion,
                'Person': MockPerson
            };
            createController = function() {
                $injector.get('$controller')("DiscursiveAnswerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:discursiveAnswerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
