package com.teachit.repository;

import com.teachit.domain.ApplicationAdmission;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ApplicationAdmission entity.
 */
@SuppressWarnings("unused")
public interface ApplicationAdmissionRepository extends JpaRepository<ApplicationAdmission,Long> {

}
