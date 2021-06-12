package ng.com.dpros.customermanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ServiceCategory.
 */
@Entity
@Table(name = "service_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "servicecategory")
public class ServiceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fixed_amount", precision = 21, scale = 2)
    private BigDecimal fixedAmount;

    @Column(name = "has_fixed_price")
    private Boolean hasFixedPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ServiceCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    public ServiceCategory fixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
        return this;
    }

    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public Boolean isHasFixedPrice() {
        return hasFixedPrice;
    }

    public ServiceCategory hasFixedPrice(Boolean hasFixedPrice) {
        this.hasFixedPrice = hasFixedPrice;
        return this;
    }

    public void setHasFixedPrice(Boolean hasFixedPrice) {
        this.hasFixedPrice = hasFixedPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceCategory)) {
            return false;
        }
        return id != null && id.equals(((ServiceCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fixedAmount=" + getFixedAmount() +
            ", hasFixedPrice='" + isHasFixedPrice() + "'" +
            "}";
    }
}
