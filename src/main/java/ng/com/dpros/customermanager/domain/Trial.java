package ng.com.dpros.customermanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Trial.
 */
@Entity
@Table(name = "trial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trial")
public class Trial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private Float name;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "example")
    private Double example;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getName() {
        return name;
    }

    public Trial name(Float name) {
        this.name = name;
        return this;
    }

    public void setName(Float name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public Trial fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getExample() {
        return example;
    }

    public Trial example(Double example) {
        this.example = example;
        return this;
    }

    public void setExample(Double example) {
        this.example = example;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trial)) {
            return false;
        }
        return id != null && id.equals(((Trial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trial{" +
            "id=" + getId() +
            ", name=" + getName() +
            ", fullName='" + getFullName() + "'" +
            ", example=" + getExample() +
            "}";
    }
}
