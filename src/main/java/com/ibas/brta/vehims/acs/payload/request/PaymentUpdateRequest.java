package com.ibas.brta.vehims.acs.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ashshakur.rahaman
 */

@Data
@NoArgsConstructor
public class PaymentUpdateRequest {

    String challanNo;

    String paymentId;

    String paidAmount;

    String transactionId;
}
