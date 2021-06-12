package ng.com.dpros.customermanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewMapperTest {

    private ReviewMapper reviewMapper;

    @BeforeEach
    public void setUp() {
        reviewMapper = new ReviewMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reviewMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reviewMapper.fromId(null)).isNull();
    }
}
