package ng.com.dpros.customermanager.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link ng.com.dpros.customermanager.domain.ServiceCategory} entity.
 */
public class ServiceCategoryDTO implements Serializable {
    
    private Long id;

    private String name;

    private BigDecimal fixedAmount;

    private Boolean hasFixedPrice;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public Boolean isHasFixedPrice() {
        return hasFixedPrice;
    }

    public void setHasFixedPrice(Boolean hasFixedPrice) {
        this.hasFixedPrice = hasFixedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceCategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((ServiceCategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fixedAmount=" + getFixedAmount() +
            ", hasFixedPrice='" + isHasFixedPrice() + "'" +
            "}";
    }
}
