package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.*;
import ng.com.dpros.customermanager.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Review} and its DTO {@link ReviewDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class, HardwareMapper.class, TrainingMapper.class, SoftwareMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.phoneNumber", target = "profilePhoneNumber")
    @Mapping(source = "hardware.id", target = "hardwareId")
    @Mapping(source = "hardware.gadget", target = "hardwareGadget")
    @Mapping(source = "training.id", target = "trainingId")
    @Mapping(source = "training.name", target = "trainingName")
    @Mapping(source = "software.id", target = "softwareId")
    @Mapping(source = "software.technology", target = "softwareTechnology")
    ReviewDTO toDto(Review review);

    @Mapping(source = "profileId", target = "profile")
    @Mapping(source = "hardwareId", target = "hardware")
    @Mapping(source = "trainingId", target = "training")
    @Mapping(source = "softwareId", target = "software")
    Review toEntity(ReviewDTO reviewDTO);

    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
