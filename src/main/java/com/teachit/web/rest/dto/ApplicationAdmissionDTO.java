package com.teachit.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ApplicationAdmission entity.
 */
public class ApplicationAdmissionDTO implements Serializable {

    private Long id;

    private LocalDate requestDate;

    private Boolean accepted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationAdmissionDTO applicationAdmissionDTO = (ApplicationAdmissionDTO) o;

        if ( ! Objects.equals(id, applicationAdmissionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicationAdmissionDTO{" +
            "id=" + id +
            ", requestDate='" + requestDate + "'" +
            ", accepted='" + accepted + "'" +
            '}';
    }
}
