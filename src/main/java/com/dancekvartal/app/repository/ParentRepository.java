package com.dancekvartal.app.repository;

import com.dancekvartal.app.domain.Parent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Parent entity.
 */
@SuppressWarnings("unused")
public interface ParentRepository extends JpaRepository<Parent,Long> {

}
