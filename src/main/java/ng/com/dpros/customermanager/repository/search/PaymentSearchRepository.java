package ng.com.dpros.customermanager.repository.search;

import ng.com.dpros.customermanager.domain.Payment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Payment} entity.
 */
public interface PaymentSearchRepository extends ElasticsearchRepository<Payment, Long> {
}
