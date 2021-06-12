package ng.com.dpros.customermanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ng.com.dpros.customermanager.domain.Review} entity. This class is used
 * in {@link ng.com.dpros.customermanager.web.rest.ReviewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReviewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter rating;

    private StringFilter comment;

    private LongFilter profileId;

    private LongFilter hardwareId;

    private LongFilter trainingId;

    private LongFilter softwareId;

    public ReviewCriteria() {
    }

    public ReviewCriteria(ReviewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.profileId = other.profileId == null ? null : other.profileId.copy();
        this.hardwareId = other.hardwareId == null ? null : other.hardwareId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
        this.softwareId = other.softwareId == null ? null : other.softwareId.copy();
    }

    @Override
    public ReviewCriteria copy() {
        return new ReviewCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getRating() {
        return rating;
    }

    public void setRating(IntegerFilter rating) {
        this.rating = rating;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public LongFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(LongFilter profileId) {
        this.profileId = profileId;
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
        final ReviewCriteria that = (ReviewCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(profileId, that.profileId) &&
            Objects.equals(hardwareId, that.hardwareId) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(softwareId, that.softwareId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rating,
        comment,
        profileId,
        hardwareId,
        trainingId,
        softwareId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
                (hardwareId != null ? "hardwareId=" + hardwareId + ", " : "") +
                (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
                (softwareId != null ? "softwareId=" + softwareId + ", " : "") +
            "}";
    }

}
