package com.dancekvartal.app.repository.search;

import com.dancekvartal.app.domain.Parent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Parent entity.
 */
public interface ParentSearchRepository extends ElasticsearchRepository<Parent, Long> {
}
