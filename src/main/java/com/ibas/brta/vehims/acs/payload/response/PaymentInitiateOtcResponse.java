package com.ibas.brta.vehims.acs.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ashshakur.rahaman
 */
@Getter
@Setter
@NoArgsConstructor
public class PaymentInitiateOtcResponse {

    @JsonProperty("responsecode")
    String responsecode;
    @JsonProperty("tracking_no")
    String tracking_no;
}
