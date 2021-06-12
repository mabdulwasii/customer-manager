package ng.com.dpros.customermanager.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link ng.com.dpros.customermanager.domain.Services} entity.
 */
public class ServicesDTO implements Serializable {
    
    private Long id;

    private String description;

    private Instant startDate;

    private Boolean agree;

    private String signDocUrl;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Boolean isAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getSignDocUrl() {
        return signDocUrl;
    }

    public void setSignDocUrl(String signDocUrl) {
        this.signDocUrl = signDocUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicesDTO)) {
            return false;
        }

        return id != null && id.equals(((ServicesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicesDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", agree='" + isAgree() + "'" +
            ", signDocUrl='" + getSignDocUrl() + "'" +
            "}";
    }
}
