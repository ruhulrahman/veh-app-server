package com.ibas.brta.vehims.acs;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ashshakur.rahaman
 */

@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "x_acs_payment_details")
@EntityListeners(AuditingEntityListener.class)
@Data
@DynamicUpdate
@Description("Prepare payment details Info to get confirmation.")
public class PaymentDetails extends AbstractPersistable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("challan_no")
    @Column(name = "challan_no", nullable = true)
    String challanNo;

    @JsonProperty("tin")
    @Column(name = "tin", nullable = true)
    String tin;

    @JsonProperty("bin")
    @Column(name = "bin", nullable = true)
    String bin;

    @JsonProperty("payment_id")
    @Column(name = "payment_id", nullable = true)
    String paymentId;

    @JsonProperty("paidamount")
    @Column(name = "paid_amount", nullable = true)
    String paidAmount;

    @JsonProperty("entry_date")
    @Column(name = "entry_date", nullable = true)
    String entryDate;

    @JsonProperty("payment_date")
    @Column(name = "payment_date", nullable = true)
    String paymentDate;

    @JsonProperty("client_name")
    @Column(name = "client_name", nullable = true)
    String clientName;

    @JsonProperty("mobile_no")
    @Column(name = "mobile_no", nullable = true)
    String mobileNo;

    @JsonProperty("email")
    @Column(name = "email", nullable = true)
    String email;

    @JsonProperty("address")
    @Column(name = "address", nullable = true)
    String address;

    @JsonProperty("bank_name")
    @Column(name = "bank_name", nullable = true)
    String bankName;

    @JsonProperty("routing_no")
    @Column(name = "routing_no", nullable = true)
    String routingNo;

    @JsonProperty("used_flag")
    @Column(name = "used_flag", nullable = true)
    String usedFlag;

    @JsonProperty("SubmissionID")
    @Column(name = "Submission_ID", nullable = true)
    String submissionId;

    @JsonProperty("DueDate")
    @Column(name = "due_date", nullable = true)
    String dueDate;

    @JsonProperty("Txt50")
    @Column(name = "TXT50", nullable = true)
    String txt50;

    @JsonProperty("PeriodKey")
    @Column(name = "PERIODKEY", nullable = true)
    String periodKey;

    @JsonProperty("SubmissionDate")
    @Column(name = "Submission_Date", nullable = true)
    String submissionDate;

    @JsonProperty("SD")
    @Column(name = "sd", nullable = true)
    String sd;

    @JsonProperty("VAT")
    @Column(name = "vat", nullable = true)
    String vat;

    @JsonProperty("Circle")
    @Column(name = "circle", nullable = true)
    String circle;

    @JsonProperty("Division")
    @Column(name = "division", nullable = true)
    String division;

    @JsonProperty("Commissionerate")
    @Column(name = "commissionerate", nullable = true)
    String commissionerate;
}
