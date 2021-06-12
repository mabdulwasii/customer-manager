package ng.com.dpros.customermanager.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import ng.com.dpros.customermanager.domain.enumeration.Gadget;

/**
 * A DTO for the {@link ng.com.dpros.customermanager.domain.Hardware} entity.
 */
public class HardwareDTO implements Serializable {
    
    private Long id;

    private Gadget gadget;

    private String model;

    private String brandName;

    private String serialNumber;

    private String imeiNumber;

    private String state;


    private Long servicesId;

    private String servicesDescription;

    private Long serviceCategoryId;

    private String serviceCategoryName;

    private Long profileId;

    private String profilePhoneNumber;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gadget getGadget() {
        return gadget;
    }

    public void setGadget(Gadget gadget) {
        this.gadget = gadget;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getServicesId() {
        return servicesId;
    }

    public void setServicesId(Long servicesId) {
        this.servicesId = servicesId;
    }

    public String getServicesDescription() {
        return servicesDescription;
    }

    public void setServicesDescription(String servicesDescription) {
        this.servicesDescription = servicesDescription;
    }

    public Long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(Long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getProfilePhoneNumber() {
        return profilePhoneNumber;
    }

    public void setProfilePhoneNumber(String profilePhoneNumber) {
        this.profilePhoneNumber = profilePhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HardwareDTO)) {
            return false;
        }

        return id != null && id.equals(((HardwareDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HardwareDTO{" +
            "id=" + getId() +
            ", gadget='" + getGadget() + "'" +
            ", model='" + getModel() + "'" +
            ", brandName='" + getBrandName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", imeiNumber='" + getImeiNumber() + "'" +
            ", state='" + getState() + "'" +
            ", servicesId=" + getServicesId() +
            ", servicesDescription='" + getServicesDescription() + "'" +
            ", serviceCategoryId=" + getServiceCategoryId() +
            ", serviceCategoryName='" + getServiceCategoryName() + "'" +
            ", profileId=" + getProfileId() +
            ", profilePhoneNumber='" + getProfilePhoneNumber() + "'" +
            "}";
    }
}
