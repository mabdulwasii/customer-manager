package ng.com.dpros.customermanager.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link ng.com.dpros.customermanager.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {
    
    private Long id;

    private Instant date;

    @NotNull
    private BigDecimal amount;

    private String paymentType;

    @DecimalMin(value = "0")
    private BigDecimal balance;


    private Long softwareId;

    private String softwareTechnology;

    private Long trainingId;

    private String trainingName;

    private Long hardwareId;

    private String hardwareGadget;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareTechnology() {
        return softwareTechnology;
    }

    public void setSoftwareTechnology(String softwareTechnology) {
        this.softwareTechnology = softwareTechnology;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getHardwareGadget() {
        return hardwareGadget;
    }

    public void setHardwareGadget(String hardwareGadget) {
        this.hardwareGadget = hardwareGadget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", balance=" + getBalance() +
            ", softwareId=" + getSoftwareId() +
            ", softwareTechnology='" + getSoftwareTechnology() + "'" +
            ", trainingId=" + getTrainingId() +
            ", trainingName='" + getTrainingName() + "'" +
            ", hardwareId=" + getHardwareId() +
            ", hardwareGadget='" + getHardwareGadget() + "'" +
            "}";
    }
}
