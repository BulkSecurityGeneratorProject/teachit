package com.teachit.repository;

import com.teachit.domain.MultipleChoiceAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MultipleChoiceAnswer entity.
 */
@SuppressWarnings("unused")
public interface MultipleChoiceAnswerRepository extends JpaRepository<MultipleChoiceAnswer,Long> {

}
