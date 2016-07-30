package com.teachit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "description")
    private String description;

    @Column(name = "text")
    private String text;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Lesson course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lesson getCourse() {
        return course;
    }

    public void setCourse(Lesson lesson) {
        this.course = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        if(content.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + id +
            ", fileName='" + fileName + "'" +
            ", startDate='" + startDate + "'" +
            ", description='" + description + "'" +
            ", text='" + text + "'" +
            '}';
    }
}
