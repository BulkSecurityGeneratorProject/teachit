package com.teachit.repository;

import com.teachit.domain.Choice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Choice entity.
 */
@SuppressWarnings("unused")
public interface ChoiceRepository extends JpaRepository<Choice,Long> {

}
