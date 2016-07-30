package com.teachit.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Course entity.
 */
public class CourseDTO implements Serializable {

    private Long id;

    private String code;

    private LocalDate startDate;

    private String name;

    private Boolean openCourse;


    private Long applicationAdmissionId;
    
    private Set<LessonDTO> lessons = new HashSet<>();

    private Long teacherId;
    

    private String teacherName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Boolean getOpenCourse() {
        return openCourse;
    }

    public void setOpenCourse(Boolean openCourse) {
        this.openCourse = openCourse;
    }

    public Long getApplicationAdmissionId() {
        return applicationAdmissionId;
    }

    public void setApplicationAdmissionId(Long applicationAdmissionId) {
        this.applicationAdmissionId = applicationAdmissionId;
    }

    public Set<LessonDTO> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonDTO> lessons) {
        this.lessons = lessons;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long personId) {
        this.teacherId = personId;
    }


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String personName) {
        this.teacherName = personName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;

        if ( ! Objects.equals(id, courseDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", startDate='" + startDate + "'" +
            ", name='" + name + "'" +
            ", openCourse='" + openCourse + "'" +
            '}';
    }
}
