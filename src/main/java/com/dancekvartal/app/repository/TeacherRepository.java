package com.dancekvartal.app.repository;

import com.dancekvartal.app.domain.Teacher;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Teacher entity.
 */
@SuppressWarnings("unused")
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

}
