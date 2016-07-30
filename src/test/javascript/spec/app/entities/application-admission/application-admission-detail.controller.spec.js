'use strict';

describe('Controller Tests', function() {

    describe('ApplicationAdmission Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockApplicationAdmission, MockPerson, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockApplicationAdmission = jasmine.createSpy('MockApplicationAdmission');
            MockPerson = jasmine.createSpy('MockPerson');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ApplicationAdmission': MockApplicationAdmission,
                'Person': MockPerson,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("ApplicationAdmissionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'teachitApp:applicationAdmissionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
