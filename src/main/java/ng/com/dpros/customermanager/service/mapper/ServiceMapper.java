package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.service.dto.ServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Services} and its DTO {@link ServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceMapper extends EntityMapper<ServiceDTO, Services> {


    @Mapping(target = "hardware", ignore = true)
    @Mapping(target = "training", ignore = true)
    @Mapping(target = "software", ignore = true)
    Services toEntity(ServiceDTO serviceDTO);

    default Services fromId(Long id) {
        if (id == null) {
            return null;
        }
        Services service = new Services();
        service.setId(id);
        return service;
    }
}
