package com.teachit.repository;

import com.teachit.domain.ContentView;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContentView entity.
 */
@SuppressWarnings("unused")
public interface ContentViewRepository extends JpaRepository<ContentView,Long> {

}
