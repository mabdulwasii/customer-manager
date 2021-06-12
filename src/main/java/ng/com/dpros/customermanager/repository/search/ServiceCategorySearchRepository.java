package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.ServiceCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ServiceCategory} entity.
 */
public interface ServiceCategorySearchRepository extends ElasticsearchRepository<ServiceCategory, Long> {
}
