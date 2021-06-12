package ng.com.dpros.customermanager.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TrialSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TrialSearchRepositoryMockConfiguration {

    @MockBean
    private TrialSearchRepository mockTrialSearchRepository;

}
