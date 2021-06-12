package ng.com.dpros.customermanager.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ng.com.dpros.customermanager.domain.Review} entity.
 */
public class ReviewDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer rating;

    private String comment;


    private Long profileId;

    private String profilePhoneNumber;

    private Long hardwareId;

    private String hardwareGadget;

    private Long trainingId;

    private String trainingName;

    private Long softwareId;

    private String softwareTechnology;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getHardwareGadget() {
        return hardwareGadget;
    }

    public void setHardwareGadget(String hardwareGadget) {
        this.hardwareGadget = hardwareGadget;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareTechnology() {
        return softwareTechnology;
    }

    public void setSoftwareTechnology(String softwareTechnology) {
        this.softwareTechnology = softwareTechnology;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewDTO)) {
            return false;
        }

        return id != null && id.equals(((ReviewDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewDTO{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", comment='" + getComment() + "'" +
            ", profileId=" + getProfileId() +
            ", profilePhoneNumber='" + getProfilePhoneNumber() + "'" +
            ", hardwareId=" + getHardwareId() +
            ", hardwareGadget='" + getHardwareGadget() + "'" +
            ", trainingId=" + getTrainingId() +
            ", trainingName='" + getTrainingName() + "'" +
            ", softwareId=" + getSoftwareId() +
            ", softwareTechnology='" + getSoftwareTechnology() + "'" +
            "}";
    }
}
