(function() {
    'use strict';

    angular
        .module('teachitApp')
        .controller('CourseDialogController', CourseDialogController);

    CourseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Course', 'ApplicationAdmission', 'MultipleChoiceQuestion', 'DiscursiveQuestion', 'Content', 'Lesson', 'Person'];

    function CourseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Course, ApplicationAdmission, MultipleChoiceQuestion, DiscursiveQuestion, Content, Lesson, Person) {
        var vm = this;

        vm.course = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.applicationadmissions = ApplicationAdmission.query({filter: 'course-is-null'});
        $q.all([vm.course.$promise, vm.applicationadmissions.$promise]).then(function() {
            if (!vm.course.applicationAdmissionId) {
                return $q.reject();
            }
            return ApplicationAdmission.get({id : vm.course.applicationAdmissionId}).$promise;
        }).then(function(applicationAdmission) {
            vm.applicationadmissions.push(applicationAdmission);
        });
        vm.multiplechoicequestions = MultipleChoiceQuestion.query();
        vm.discursivequestions = DiscursiveQuestion.query();
        vm.contents = Content.query();
        vm.lessons = Lesson.query();
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.course.id !== null) {
                Course.update(vm.course, onSaveSuccess, onSaveError);
            } else {
                Course.save(vm.course, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('teachitApp:courseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
