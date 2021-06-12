package ng.com.dpros.customermanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ng.com.dpros.customermanager.web.rest.TestUtil;

public class HardwareTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hardware.class);
        Hardware hardware1 = new Hardware();
        hardware1.setId(1L);
        Hardware hardware2 = new Hardware();
        hardware2.setId(hardware1.getId());
        assertThat(hardware1).isEqualTo(hardware2);
        hardware2.setId(2L);
        assertThat(hardware1).isNotEqualTo(hardware2);
        hardware1.setId(null);
        assertThat(hardware1).isNotEqualTo(hardware2);
    }
}
