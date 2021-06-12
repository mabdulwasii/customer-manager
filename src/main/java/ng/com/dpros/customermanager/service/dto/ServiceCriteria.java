package ng.com.dpros.customermanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import ng.com.dpros.customermanager.domain.Services;

/**
 * Criteria class for the {@link Services} entity. This class is used
 * in {@link ng.com.dpros.customermanager.web.rest.ServiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /services?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private InstantFilter startDate;

    private BooleanFilter agree;

    private StringFilter signDocUrl;

    private LongFilter hardwareId;

    private LongFilter trainingId;

    private LongFilter softwareId;

    public ServiceCriteria() {
    }

    public ServiceCriteria(ServiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.agree = other.agree == null ? null : other.agree.copy();
        this.signDocUrl = other.signDocUrl == null ? null : other.signDocUrl.copy();
        this.hardwareId = other.hardwareId == null ? null : other.hardwareId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
        this.softwareId = other.softwareId == null ? null : other.softwareId.copy();
    }

    @Override
    public ServiceCriteria copy() {
        return new ServiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public BooleanFilter getAgree() {
        return agree;
    }

    public void setAgree(BooleanFilter agree) {
        this.agree = agree;
    }

    public StringFilter getSignDocUrl() {
        return signDocUrl;
    }

    public void setSignDocUrl(StringFilter signDocUrl) {
        this.signDocUrl = signDocUrl;
    }

    public LongFilter getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(LongFilter hardwareId) {
        this.hardwareId = hardwareId;
    }

    public LongFilter getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(LongFilter trainingId) {
        this.trainingId = trainingId;
    }

    public LongFilter getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(LongFilter softwareId) {
        this.softwareId = softwareId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceCriteria that = (ServiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(agree, that.agree) &&
            Objects.equals(signDocUrl, that.signDocUrl) &&
            Objects.equals(hardwareId, that.hardwareId) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(softwareId, that.softwareId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        startDate,
        agree,
        signDocUrl,
        hardwareId,
        trainingId,
        softwareId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (agree != null ? "agree=" + agree + ", " : "") +
                (signDocUrl != null ? "signDocUrl=" + signDocUrl + ", " : "") +
                (hardwareId != null ? "hardwareId=" + hardwareId + ", " : "") +
                (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
                (softwareId != null ? "softwareId=" + softwareId + ", " : "") +
            "}";
    }

}
