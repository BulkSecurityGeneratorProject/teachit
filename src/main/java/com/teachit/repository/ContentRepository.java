package com.teachit.repository;

import com.teachit.domain.Content;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Content entity.
 */
@SuppressWarnings("unused")
public interface ContentRepository extends JpaRepository<Content,Long> {

}
