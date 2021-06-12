package ng.com.dpros.customermanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import ng.com.dpros.customermanager.domain.enumeration.Technology;

/**
 * A Software.
 */
@Entity
@Table(name = "software")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "software")
public class Software implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "technology")
    private Technology technology;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JsonIgnoreProperties(value = "software", allowSetters = true)
    private ServiceCategory serviceCategory;

    @OneToMany(mappedBy = "software")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Services services;

    @OneToMany(mappedBy = "software")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "software", allowSetters = true)
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Technology getTechnology() {
        return technology;
    }

    public Software technology(Technology technology) {
        this.technology = technology;
        return this;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Software amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public Software details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public Software serviceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
        return this;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Software payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Software addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setSoftware(this);
        return this;
    }

    public Software removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setSoftware(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Services getServices() {
        return services;
    }

    public Software services(Services services) {
        this.services = services;
        return this;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Software reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Software addReview(Review review) {
        this.reviews.add(review);
        review.setSoftware(this);
        return this;
    }

    public Software removeReview(Review review) {
        this.reviews.remove(review);
        review.setSoftware(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Profile getProfile() {
        return profile;
    }

    public Software profile(Profile profile) {
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
        if (!(o instanceof Software)) {
            return false;
        }
        return id != null && id.equals(((Software) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Software{" +
            "id=" + getId() +
            ", technology='" + getTechnology() + "'" +
            ", amount=" + getAmount() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
