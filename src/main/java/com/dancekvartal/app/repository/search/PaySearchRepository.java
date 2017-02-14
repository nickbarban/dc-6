package com.dancekvartal.app.repository.search;

import com.dancekvartal.app.domain.Pay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pay entity.
 */
public interface PaySearchRepository extends ElasticsearchRepository<Pay, Long> {
}
