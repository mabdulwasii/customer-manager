package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.*;
import ng.com.dpros.customermanager.service.dto.ServiceCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceCategory} and its DTO {@link ServiceCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceCategoryMapper extends EntityMapper<ServiceCategoryDTO, ServiceCategory> {



    default ServiceCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setId(id);
        return serviceCategory;
    }
}
