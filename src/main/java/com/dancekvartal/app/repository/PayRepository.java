package com.dancekvartal.app.repository;

import com.dancekvartal.app.domain.Pay;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pay entity.
 */
@SuppressWarnings("unused")
public interface PayRepository extends JpaRepository<Pay,Long> {

}
