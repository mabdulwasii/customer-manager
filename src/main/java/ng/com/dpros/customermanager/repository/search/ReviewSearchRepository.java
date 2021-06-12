package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.Review;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Review} entity.
 */
public interface ReviewSearchRepository extends ElasticsearchRepository<Review, Long> {
}
