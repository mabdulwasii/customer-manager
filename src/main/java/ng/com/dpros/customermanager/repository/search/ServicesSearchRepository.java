package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.Services;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Services} entity.
 */
public interface ServicesSearchRepository extends ElasticsearchRepository<Services, Long> {
}
