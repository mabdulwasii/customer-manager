package ng.com.dpros.customermanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link ng.com.dpros.customermanager.domain.Payment} entity. This class is used
 * in {@link ng.com.dpros.customermanager.web.rest.PaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private BigDecimalFilter amount;

    private StringFilter paymentType;

    private BigDecimalFilter balance;

    private LongFilter softwareId;

    private LongFilter trainingId;

    private LongFilter hardwareId;

    public PaymentCriteria() {
    }

    public PaymentCriteria(PaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.balance = other.balance == null ? null : other.balance.copy();
        this.softwareId = other.softwareId == null ? null : other.softwareId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
        this.hardwareId = other.hardwareId == null ? null : other.hardwareId.copy();
    }

    @Override
    public PaymentCriteria copy() {
        return new PaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(StringFilter paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimalFilter getBalance() {
        return balance;
    }

    public void setBalance(BigDecimalFilter balance) {
        this.balance = balance;
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

    public LongFilter getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(LongFilter hardwareId) {
        this.hardwareId = hardwareId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentCriteria that = (PaymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(balance, that.balance) &&
            Objects.equals(softwareId, that.softwareId) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(hardwareId, that.hardwareId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        amount,
        paymentType,
        balance,
        softwareId,
        trainingId,
        hardwareId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (softwareId != null ? "softwareId=" + softwareId + ", " : "") +
                (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
                (hardwareId != null ? "hardwareId=" + hardwareId + ", " : "") +
            "}";
    }

}
