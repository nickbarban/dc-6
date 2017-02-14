package com.dancekvartal.app.repository;

import com.dancekvartal.app.domain.Subject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subject entity.
 */
@SuppressWarnings("unused")
public interface SubjectRepository extends JpaRepository<Subject,Long> {

}
