package ng.com.dpros.customermanager.service.mapper;


import ng.com.dpros.customermanager.domain.*;
import ng.com.dpros.customermanager.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {SoftwareMapper.class, TrainingMapper.class, HardwareMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "software.id", target = "softwareId")
    @Mapping(source = "software.technology", target = "softwareTechnology")
    @Mapping(source = "training.id", target = "trainingId")
    @Mapping(source = "training.name", target = "trainingName")
    @Mapping(source = "hardware.id", target = "hardwareId")
    @Mapping(source = "hardware.gadget", target = "hardwareGadget")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "softwareId", target = "software")
    @Mapping(source = "trainingId", target = "training")
    @Mapping(source = "hardwareId", target = "hardware")
    Payment toEntity(PaymentDTO paymentDTO);

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
