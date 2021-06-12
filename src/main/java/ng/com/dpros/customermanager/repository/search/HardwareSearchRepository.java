package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.Hardware;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Hardware} entity.
 */
public interface HardwareSearchRepository extends ElasticsearchRepository<Hardware, Long> {
}
