package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.*;
import ng.com.dpros.customermanager.service.dto.ServicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Services} and its DTO {@link ServicesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServicesMapper extends EntityMapper<ServicesDTO, Services> {


    @Mapping(target = "hardware", ignore = true)
    @Mapping(target = "training", ignore = true)
    @Mapping(target = "software", ignore = true)
    Services toEntity(ServicesDTO servicesDTO);

    default Services fromId(Long id) {
        if (id == null) {
            return null;
        }
        Services services = new Services();
        services.setId(id);
        return services;
    }
}
