package ng.com.dpros.customermanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ng.com.dpros.customermanager.domain.enumeration.Gender;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "profile_id")
    private String profileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "valid_id")
    private String validId;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Hardware> hardware = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Software> software = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Training> trainings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Profile phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Profile dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfileId() {
        return profileId;
    }

    public Profile profileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Gender getGender() {
        return gender;
    }

    public Profile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getValidId() {
        return validId;
    }

    public Profile validId(String validId) {
        this.validId = validId;
        return this;
    }

    public void setValidId(String validId) {
        this.validId = validId;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public Profile address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Hardware> getHardware() {
        return hardware;
    }

    public Profile hardware(Set<Hardware> hardware) {
        this.hardware = hardware;
        return this;
    }

    public Profile addHardware(Hardware hardware) {
        this.hardware.add(hardware);
        hardware.setProfile(this);
        return this;
    }

    public Profile removeHardware(Hardware hardware) {
        this.hardware.remove(hardware);
        hardware.setProfile(null);
        return this;
    }

    public void setHardware(Set<Hardware> hardware) {
        this.hardware = hardware;
    }

    public Set<Software> getSoftware() {
        return software;
    }

    public Profile software(Set<Software> software) {
        this.software = software;
        return this;
    }

    public Profile addSoftware(Software software) {
        this.software.add(software);
        software.setProfile(this);
        return this;
    }

    public Profile removeSoftware(Software software) {
        this.software.remove(software);
        software.setProfile(null);
        return this;
    }

    public void setSoftware(Set<Software> software) {
        this.software = software;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public Profile trainings(Set<Training> trainings) {
        this.trainings = trainings;
        return this;
    }

    public Profile addTraining(Training training) {
        this.trainings.add(training);
        training.setProfile(this);
        return this;
    }

    public Profile removeTraining(Training training) {
        this.trainings.remove(training);
        training.setProfile(null);
        return this;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", profileId='" + getProfileId() + "'" +
            ", gender='" + getGender() + "'" +
            ", validId='" + getValidId() + "'" +
            "}";
    }
}
