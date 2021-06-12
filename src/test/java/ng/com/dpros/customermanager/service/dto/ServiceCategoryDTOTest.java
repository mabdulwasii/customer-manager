package ng.com.dpros.customermanager.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ng.com.dpros.customermanager.web.rest.TestUtil;

public class ServiceCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceCategoryDTO.class);
        ServiceCategoryDTO serviceCategoryDTO1 = new ServiceCategoryDTO();
        serviceCategoryDTO1.setId(1L);
        ServiceCategoryDTO serviceCategoryDTO2 = new ServiceCategoryDTO();
        assertThat(serviceCategoryDTO1).isNotEqualTo(serviceCategoryDTO2);
        serviceCategoryDTO2.setId(serviceCategoryDTO1.getId());
        assertThat(serviceCategoryDTO1).isEqualTo(serviceCategoryDTO2);
        serviceCategoryDTO2.setId(2L);
        assertThat(serviceCategoryDTO1).isNotEqualTo(serviceCategoryDTO2);
        serviceCategoryDTO1.setId(null);
        assertThat(serviceCategoryDTO1).isNotEqualTo(serviceCategoryDTO2);
    }
}
