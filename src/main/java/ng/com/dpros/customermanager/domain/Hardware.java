package ng.com.dpros.customermanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import ng.com.dpros.customermanager.domain.enumeration.Gadget;

/**
 * A Hardware.
 */
@Entity
@Table(name = "hardware")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "hardware")
public class Hardware implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gadget")
    private Gadget gadget;

    @Column(name = "model")
    private String model;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "imei_number")
    private String imeiNumber;

    @Column(name = "state")
    private String state;

    @OneToOne
    @JoinColumn(unique = true)
    private Services services;

    @OneToMany(mappedBy = "hardware")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "hardware")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "hardware", allowSetters = true)
    private ServiceCategory serviceCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "hardware", allowSetters = true)
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gadget getGadget() {
        return gadget;
    }

    public Hardware gadget(Gadget gadget) {
        this.gadget = gadget;
        return this;
    }

    public void setGadget(Gadget gadget) {
        this.gadget = gadget;
    }

    public String getModel() {
        return model;
    }

    public Hardware model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrandName() {
        return brandName;
    }

    public Hardware brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Hardware serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public Hardware imeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
        return this;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getState() {
        return state;
    }

    public Hardware state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Services getServices() {
        return services;
    }

    public Hardware services(Services services) {
        this.services = services;
        return this;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Hardware reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Hardware addReview(Review review) {
        this.reviews.add(review);
        review.setHardware(this);
        return this;
    }

    public Hardware removeReview(Review review) {
        this.reviews.remove(review);
        review.setHardware(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Hardware payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Hardware addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setHardware(this);
        return this;
    }

    public Hardware removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setHardware(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public Hardware serviceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
        return this;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public Profile getProfile() {
        return profile;
    }

    public Hardware profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hardware)) {
            return false;
        }
        return id != null && id.equals(((Hardware) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hardware{" +
            "id=" + getId() +
            ", gadget='" + getGadget() + "'" +
            ", model='" + getModel() + "'" +
            ", brandName='" + getBrandName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", imeiNumber='" + getImeiNumber() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
