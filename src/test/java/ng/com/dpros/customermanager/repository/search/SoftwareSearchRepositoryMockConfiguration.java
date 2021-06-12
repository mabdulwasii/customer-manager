package ng.com.dpros.customermanager.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SoftwareSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SoftwareSearchRepositoryMockConfiguration {

    @MockBean
    private SoftwareSearchRepository mockSoftwareSearchRepository;

}
