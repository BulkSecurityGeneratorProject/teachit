package com.teachit.repository;

import com.teachit.domain.MultipleChoiceQuestion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MultipleChoiceQuestion entity.
 */
@SuppressWarnings("unused")
public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion,Long> {

}
