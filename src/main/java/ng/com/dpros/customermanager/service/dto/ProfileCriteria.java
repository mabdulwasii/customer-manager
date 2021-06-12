package ng.com.dpros.customermanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ng.com.dpros.customermanager.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link ng.com.dpros.customermanager.domain.Profile} entity. This class is used
 * in {@link ng.com.dpros.customermanager.web.rest.ProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfileCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phoneNumber;

    private LocalDateFilter dateOfBirth;

    private StringFilter profileId;

    private GenderFilter gender;

    private StringFilter validId;

    private LongFilter userId;

    private LongFilter addressId;

    private LongFilter hardwareId;

    private LongFilter softwareId;

    private LongFilter trainingId;

    public ProfileCriteria() {
    }

    public ProfileCriteria(ProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.profileId = other.profileId == null ? null : other.profileId.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.validId = other.validId == null ? null : other.validId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.hardwareId = other.hardwareId == null ? null : other.hardwareId.copy();
        this.softwareId = other.softwareId == null ? null : other.softwareId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
    }

    @Override
    public ProfileCriteria copy() {
        return new ProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(StringFilter profileId) {
        this.profileId = profileId;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getValidId() {
        return validId;
    }

    public void setValidId(StringFilter validId) {
        this.validId = validId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }

    public LongFilter getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(LongFilter hardwareId) {
        this.hardwareId = hardwareId;
    }

    public LongFilter getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(LongFilter softwareId) {
        this.softwareId = softwareId;
    }

    public LongFilter getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(LongFilter trainingId) {
        this.trainingId = trainingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProfileCriteria that = (ProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(profileId, that.profileId) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(validId, that.validId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(hardwareId, that.hardwareId) &&
            Objects.equals(softwareId, that.softwareId) &&
            Objects.equals(trainingId, that.trainingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phoneNumber,
        dateOfBirth,
        profileId,
        gender,
        validId,
        userId,
        addressId,
        hardwareId,
        softwareId,
        trainingId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (validId != null ? "validId=" + validId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
                (hardwareId != null ? "hardwareId=" + hardwareId + ", " : "") +
                (softwareId != null ? "softwareId=" + softwareId + ", " : "") +
                (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
            "}";
    }

}
