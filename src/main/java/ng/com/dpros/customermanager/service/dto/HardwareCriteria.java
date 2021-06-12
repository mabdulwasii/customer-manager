package ng.com.dpros.customermanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ng.com.dpros.customermanager.domain.enumeration.Gadget;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ng.com.dpros.customermanager.domain.Hardware} entity. This class is used
 * in {@link ng.com.dpros.customermanager.web.rest.HardwareResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hardware?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HardwareCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gadget
     */
    public static class GadgetFilter extends Filter<Gadget> {

        public GadgetFilter() {
        }

        public GadgetFilter(GadgetFilter filter) {
            super(filter);
        }

        @Override
        public GadgetFilter copy() {
            return new GadgetFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private GadgetFilter gadget;

    private StringFilter model;

    private StringFilter brandName;

    private StringFilter serialNumber;

    private StringFilter imeiNumber;

    private StringFilter state;

    private LongFilter servicesId;

    private LongFilter reviewId;

    private LongFilter paymentId;

    private LongFilter serviceCategoryId;

    private LongFilter profileId;

    public HardwareCriteria() {
    }

    public HardwareCriteria(HardwareCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.gadget = other.gadget == null ? null : other.gadget.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.brandName = other.brandName == null ? null : other.brandName.copy();
        this.serialNumber = other.serialNumber == null ? null : other.serialNumber.copy();
        this.imeiNumber = other.imeiNumber == null ? null : other.imeiNumber.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.servicesId = other.servicesId == null ? null : other.servicesId.copy();
        this.reviewId = other.reviewId == null ? null : other.reviewId.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.serviceCategoryId = other.serviceCategoryId == null ? null : other.serviceCategoryId.copy();
        this.profileId = other.profileId == null ? null : other.profileId.copy();
    }

    @Override
    public HardwareCriteria copy() {
        return new HardwareCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public GadgetFilter getGadget() {
        return gadget;
    }

    public void setGadget(GadgetFilter gadget) {
        this.gadget = gadget;
    }

    public StringFilter getModel() {
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public StringFilter getBrandName() {
        return brandName;
    }

    public void setBrandName(StringFilter brandName) {
        this.brandName = brandName;
    }

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
    }

    public StringFilter getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(StringFilter imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public LongFilter getServicesId() {
        return servicesId;
    }

    public void setServicesId(LongFilter servicesId) {
        this.servicesId = servicesId;
    }

    public LongFilter getReviewId() {
        return reviewId;
    }

    public void setReviewId(LongFilter reviewId) {
        this.reviewId = reviewId;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public LongFilter getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(LongFilter serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public LongFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(LongFilter profileId) {
        this.profileId = profileId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HardwareCriteria that = (HardwareCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(gadget, that.gadget) &&
            Objects.equals(model, that.model) &&
            Objects.equals(brandName, that.brandName) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(imeiNumber, that.imeiNumber) &&
            Objects.equals(state, that.state) &&
            Objects.equals(servicesId, that.servicesId) &&
            Objects.equals(reviewId, that.reviewId) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(serviceCategoryId, that.serviceCategoryId) &&
            Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        gadget,
        model,
        brandName,
        serialNumber,
        imeiNumber,
        state,
        servicesId,
        reviewId,
        paymentId,
        serviceCategoryId,
        profileId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HardwareCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (gadget != null ? "gadget=" + gadget + ", " : "") +
                (model != null ? "model=" + model + ", " : "") +
                (brandName != null ? "brandName=" + brandName + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (imeiNumber != null ? "imeiNumber=" + imeiNumber + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (servicesId != null ? "servicesId=" + servicesId + ", " : "") +
                (reviewId != null ? "reviewId=" + reviewId + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
                (serviceCategoryId != null ? "serviceCategoryId=" + serviceCategoryId + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
            "}";
    }

}
