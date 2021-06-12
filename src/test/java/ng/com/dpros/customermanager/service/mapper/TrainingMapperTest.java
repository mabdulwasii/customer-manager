package ng.com.dpros.customermanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrainingMapperTest {

    private TrainingMapper trainingMapper;

    @BeforeEach
    public void setUp() {
        trainingMapper = new TrainingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(trainingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(trainingMapper.fromId(null)).isNull();
    }
}
