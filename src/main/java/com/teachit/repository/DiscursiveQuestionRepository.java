package com.teachit.repository;

import com.teachit.domain.DiscursiveQuestion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DiscursiveQuestion entity.
 */
@SuppressWarnings("unused")
public interface DiscursiveQuestionRepository extends JpaRepository<DiscursiveQuestion,Long> {

}
