package ng.com.dpros.customermanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Services.
 */
@Entity
@Table(name = "services")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "services")
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "agree")
    private Boolean agree;

    @Column(name = "sign_doc_url")
    private String signDocUrl;

    @OneToOne(mappedBy = "services")
    @JsonIgnore
    private Hardware hardware;

    @OneToOne(mappedBy = "services")
    @JsonIgnore
    private Training training;

    @OneToOne(mappedBy = "services")
    @JsonIgnore
    private Software software;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Services description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Services startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Boolean isAgree() {
        return agree;
    }

    public Services agree(Boolean agree) {
        this.agree = agree;
        return this;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getSignDocUrl() {
        return signDocUrl;
    }

    public Services signDocUrl(String signDocUrl) {
        this.signDocUrl = signDocUrl;
        return this;
    }

    public void setSignDocUrl(String signDocUrl) {
        this.signDocUrl = signDocUrl;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public Services hardware(Hardware hardware) {
        this.hardware = hardware;
        return this;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public Training getTraining() {
        return training;
    }

    public Services training(Training training) {
        this.training = training;
        return this;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Software getSoftware() {
        return software;
    }

    public Services software(Software software) {
        this.software = software;
        return this;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Services)) {
            return false;
        }
        return id != null && id.equals(((Services) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Services{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", agree='" + isAgree() + "'" +
            ", signDocUrl='" + getSignDocUrl() + "'" +
            "}";
    }
}
