package com.teachit.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Lesson entity.
 */
public class LessonDTO implements Serializable {

    private Long id;

    private Integer ordering;

    private LocalDate startDate;

    private String name;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LessonDTO lessonDTO = (LessonDTO) o;

        if ( ! Objects.equals(id, lessonDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LessonDTO{" +
            "id=" + id +
            ", ordering='" + ordering + "'" +
            ", startDate='" + startDate + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
