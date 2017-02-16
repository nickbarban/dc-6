package com.dancekvartal.app.repository;

import com.dancekvartal.app.domain.Student;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("select distinct student from Student student left join fetch student.subjects left join fetch student.parents")
    List<Student> findAllWithEagerRelationships();

    @Query("select student from Student student left join fetch student.subjects left join fetch student.parents where student.id =:id")
    Student findOneWithEagerRelationships(@Param("id") Long id);

}
