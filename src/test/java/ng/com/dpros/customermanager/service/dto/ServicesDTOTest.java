package ng.com.dpros.customermanager.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ng.com.dpros.customermanager.web.rest.TestUtil;

public class ServicesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicesDTO.class);
        ServicesDTO servicesDTO1 = new ServicesDTO();
        servicesDTO1.setId(1L);
        ServicesDTO servicesDTO2 = new ServicesDTO();
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
        servicesDTO2.setId(servicesDTO1.getId());
        assertThat(servicesDTO1).isEqualTo(servicesDTO2);
        servicesDTO2.setId(2L);
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
        servicesDTO1.setId(null);
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
    }
}
