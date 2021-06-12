package ng.com.dpros.customermanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HardwareMapperTest {

    private HardwareMapper hardwareMapper;

    @BeforeEach
    public void setUp() {
        hardwareMapper = new HardwareMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(hardwareMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(hardwareMapper.fromId(null)).isNull();
    }
}
