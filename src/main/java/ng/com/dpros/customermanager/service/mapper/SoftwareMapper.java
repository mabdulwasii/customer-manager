package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.*;
import ng.com.dpros.customermanager.service.dto.SoftwareDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Software} and its DTO {@link SoftwareDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceCategoryMapper.class, ServicesMapper.class, ProfileMapper.class})
public interface SoftwareMapper extends EntityMapper<SoftwareDTO, Software> {

    @Mapping(source = "serviceCategory.id", target = "serviceCategoryId")
    @Mapping(source = "serviceCategory.name", target = "serviceCategoryName")
    @Mapping(source = "services.id", target = "servicesId")
    @Mapping(source = "services.description", target = "servicesDescription")
    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.phoneNumber", target = "profilePhoneNumber")
    SoftwareDTO toDto(Software software);

    @Mapping(source = "serviceCategoryId", target = "serviceCategory")
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "removePayment", ignore = true)
    @Mapping(source = "servicesId", target = "services")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "removeReview", ignore = true)
    @Mapping(source = "profileId", target = "profile")
    Software toEntity(SoftwareDTO softwareDTO);

    default Software fromId(Long id) {
        if (id == null) {
            return null;
        }
        Software software = new Software();
        software.setId(id);
        return software;
    }
}
