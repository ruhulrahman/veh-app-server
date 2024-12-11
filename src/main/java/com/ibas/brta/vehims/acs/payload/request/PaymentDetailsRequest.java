package com.ibas.brta.vehims.acs.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ashshakur.rahaman
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsRequest {

    @JsonProperty("challan_no")
    String challanno;

    @JsonProperty("paymentid")
    String paymentid;
}
