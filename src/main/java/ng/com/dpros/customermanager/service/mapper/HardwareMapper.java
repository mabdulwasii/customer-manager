package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.*;
import ng.com.dpros.customermanager.service.dto.HardwareDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hardware} and its DTO {@link HardwareDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServicesMapper.class, ServiceCategoryMapper.class, ProfileMapper.class})
public interface HardwareMapper extends EntityMapper<HardwareDTO, Hardware> {

    @Mapping(source = "services.id", target = "servicesId")
    @Mapping(source = "services.description", target = "servicesDescription")
    @Mapping(source = "serviceCategory.id", target = "serviceCategoryId")
    @Mapping(source = "serviceCategory.name", target = "serviceCategoryName")
    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.phoneNumber", target = "profilePhoneNumber")
    HardwareDTO toDto(Hardware hardware);

    @Mapping(source = "servicesId", target = "services")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "removeReview", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "removePayment", ignore = true)
    @Mapping(source = "serviceCategoryId", target = "serviceCategory")
    @Mapping(source = "profileId", target = "profile")
    Hardware toEntity(HardwareDTO hardwareDTO);

    default Hardware fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hardware hardware = new Hardware();
        hardware.setId(id);
        return hardware;
    }
}
