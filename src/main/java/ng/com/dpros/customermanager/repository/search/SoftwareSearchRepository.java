package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.Software;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Software} entity.
 */
public interface SoftwareSearchRepository extends ElasticsearchRepository<Software, Long> {
}
