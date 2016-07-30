package com.teachit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "name")
    private String name;

    @Column(name = "open_course")
    private Boolean openCourse;

    @OneToOne
    @JoinColumn(unique = true)
    private ApplicationAdmission applicationAdmission;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<MultipleChoiceQuestion> multipleChoices = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<DiscursiveQuestion> discursiveAnswers = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<Content> contents = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "course_lessons",
               joinColumns = @JoinColumn(name="courses_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="lessons_id", referencedColumnName="ID"))
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToOne
    private Person teacher;

    @ManyToMany(mappedBy = "learningCoursees")
    @JsonIgnore
    private Set<Person> students = new HashSet<>();

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

    public Boolean isOpenCourse() {
        return openCourse;
    }

    public void setOpenCourse(Boolean openCourse) {
        this.openCourse = openCourse;
    }

    public ApplicationAdmission getApplicationAdmission() {
        return applicationAdmission;
    }

    public void setApplicationAdmission(ApplicationAdmission applicationAdmission) {
        this.applicationAdmission = applicationAdmission;
    }

    public Set<MultipleChoiceQuestion> getMultipleChoices() {
        return multipleChoices;
    }

    public void setMultipleChoices(Set<MultipleChoiceQuestion> multipleChoiceQuestions) {
        this.multipleChoices = multipleChoiceQuestions;
    }

    public Set<DiscursiveQuestion> getDiscursiveAnswers() {
        return discursiveAnswers;
    }

    public void setDiscursiveAnswers(Set<DiscursiveQuestion> discursiveQuestions) {
        this.discursiveAnswers = discursiveQuestions;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person person) {
        this.teacher = person;
    }

    public Set<Person> getStudents() {
        return students;
    }

    public void setStudents(Set<Person> people) {
        this.students = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        if(course.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", startDate='" + startDate + "'" +
            ", name='" + name + "'" +
            ", openCourse='" + openCourse + "'" +
            '}';
    }
}
