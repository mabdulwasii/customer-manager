package ng.com.dpros.customermanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServicesMapperTest {

    private ServicesMapper servicesMapper;

    @BeforeEach
    public void setUp() {
        servicesMapper = new ServicesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(servicesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(servicesMapper.fromId(null)).isNull();
    }
}
