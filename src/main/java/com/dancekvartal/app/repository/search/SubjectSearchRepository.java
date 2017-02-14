package com.dancekvartal.app.repository.search;

import com.dancekvartal.app.domain.Subject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Subject entity.
 */
public interface SubjectSearchRepository extends ElasticsearchRepository<Subject, Long> {
}
