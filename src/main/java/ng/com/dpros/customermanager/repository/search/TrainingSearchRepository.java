package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.Training;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Training} entity.
 */
public interface TrainingSearchRepository extends ElasticsearchRepository<Training, Long> {
}
