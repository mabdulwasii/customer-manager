package ng.com.dpros.customermanager.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import ng.com.dpros.customermanager.domain.enumeration.Technology;

/**
 * A DTO for the {@link ng.com.dpros.customermanager.domain.Software} entity.
 */
public class SoftwareDTO implements Serializable {
    
    private Long id;

    private Technology technology;

    private BigDecimal amount;

    private String details;


    private Long serviceCategoryId;

    private String serviceCategoryName;

    private Long servicesId;

    private String servicesDescription;

    private Long profileId;

    private String profilePhoneNumber;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
        if (!(o instanceof SoftwareDTO)) {
            return false;
        }

        return id != null && id.equals(((SoftwareDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoftwareDTO{" +
            "id=" + getId() +
            ", technology='" + getTechnology() + "'" +
            ", amount=" + getAmount() +
            ", details='" + getDetails() + "'" +
            ", serviceCategoryId=" + getServiceCategoryId() +
            ", serviceCategoryName='" + getServiceCategoryName() + "'" +
            ", servicesId=" + getServicesId() +
            ", servicesDescription='" + getServicesDescription() + "'" +
            ", profileId=" + getProfileId() +
            ", profilePhoneNumber='" + getProfilePhoneNumber() + "'" +
            "}";
    }
}
