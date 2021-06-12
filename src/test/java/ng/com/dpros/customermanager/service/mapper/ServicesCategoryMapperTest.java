package ng.com.dpros.customermanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServicesCategoryMapperTest {

    private ServiceCategoryMapper serviceCategoryMapper;

    @BeforeEach
    public void setUp() {
        serviceCategoryMapper = new ServiceCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceCategoryMapper.fromId(null)).isNull();
    }
}
