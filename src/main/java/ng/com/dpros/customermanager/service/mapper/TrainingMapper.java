package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.*;
import ng.com.dpros.customermanager.service.dto.TrainingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Training} and its DTO {@link TrainingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class, ServiceCategoryMapper.class, ServicesMapper.class})
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.phoneNumber", target = "profilePhoneNumber")
    @Mapping(source = "serviceCategory.id", target = "serviceCategoryId")
    @Mapping(source = "serviceCategory.name", target = "serviceCategoryName")
    @Mapping(source = "services.id", target = "servicesId")
    @Mapping(source = "services.description", target = "servicesDescription")
    TrainingDTO toDto(Training training);

    @Mapping(source = "profileId", target = "profile")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "removeReview", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "removePayment", ignore = true)
    @Mapping(source = "serviceCategoryId", target = "serviceCategory")
    @Mapping(source = "servicesId", target = "services")
    Training toEntity(TrainingDTO trainingDTO);

    default Training fromId(Long id) {
        if (id == null) {
            return null;
        }
        Training training = new Training();
        training.setId(id);
        return training;
    }
}
