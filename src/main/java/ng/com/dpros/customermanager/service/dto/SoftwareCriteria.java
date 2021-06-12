package ng.com.dpros.customermanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ng.com.dpros.customermanager.domain.enumeration.Technology;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link ng.com.dpros.customermanager.domain.Software} entity. This class is used
 * in {@link ng.com.dpros.customermanager.web.rest.SoftwareResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /software?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SoftwareCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Technology
     */
    public static class TechnologyFilter extends Filter<Technology> {

        public TechnologyFilter() {
        }

        public TechnologyFilter(TechnologyFilter filter) {
            super(filter);
        }

        @Override
        public TechnologyFilter copy() {
            return new TechnologyFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TechnologyFilter technology;

    private BigDecimalFilter amount;

    private StringFilter details;

    private LongFilter serviceCategoryId;

    private LongFilter paymentId;

    private LongFilter servicesId;

    private LongFilter reviewId;

    private LongFilter profileId;

    public SoftwareCriteria() {
    }

    public SoftwareCriteria(SoftwareCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.technology = other.technology == null ? null : other.technology.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.serviceCategoryId = other.serviceCategoryId == null ? null : other.serviceCategoryId.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.servicesId = other.servicesId == null ? null : other.servicesId.copy();
        this.reviewId = other.reviewId == null ? null : other.reviewId.copy();
        this.profileId = other.profileId == null ? null : other.profileId.copy();
    }

    @Override
    public SoftwareCriteria copy() {
        return new SoftwareCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TechnologyFilter getTechnology() {
        return technology;
    }

    public void setTechnology(TechnologyFilter technology) {
        this.technology = technology;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getDetails() {
        return details;
    }

    public void setDetails(StringFilter details) {
        this.details = details;
    }

    public LongFilter getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(LongFilter serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
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
        final SoftwareCriteria that = (SoftwareCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(technology, that.technology) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(details, that.details) &&
            Objects.equals(serviceCategoryId, that.serviceCategoryId) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(servicesId, that.servicesId) &&
            Objects.equals(reviewId, that.reviewId) &&
            Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        technology,
        amount,
        details,
        serviceCategoryId,
        paymentId,
        servicesId,
        reviewId,
        profileId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoftwareCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (technology != null ? "technology=" + technology + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (details != null ? "details=" + details + ", " : "") +
                (serviceCategoryId != null ? "serviceCategoryId=" + serviceCategoryId + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
                (servicesId != null ? "servicesId=" + servicesId + ", " : "") +
                (reviewId != null ? "reviewId=" + reviewId + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
            "}";
    }

}
