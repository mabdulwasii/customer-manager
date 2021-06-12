package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.Trial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Trial} entity.
 */
public interface TrialSearchRepository extends ElasticsearchRepository<Trial, Long> {
}
