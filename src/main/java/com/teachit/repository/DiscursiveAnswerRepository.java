package com.teachit.repository;

import com.teachit.domain.DiscursiveAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DiscursiveAnswer entity.
 */
@SuppressWarnings("unused")
public interface DiscursiveAnswerRepository extends JpaRepository<DiscursiveAnswer,Long> {

}
