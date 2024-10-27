package com.ibas.brta.vehims.acs;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ashshakur.rahaman
 */

 @Getter
 @Setter
 @Data
 @NoArgsConstructor
public class PaymentInitiateRequest {
    @JsonProperty("paymentid")
    String paymentid;
    @JsonProperty("paidamount")
    String paidamount;
    @JsonProperty("paymenttype")
    String paymenttype;
    @JsonProperty("organization_code")
    String organizationCode;
    @JsonProperty("tin")
    String tin;
    @JsonProperty("mobile")
    String mobile; 
    @JsonProperty("addleconomic")
    AddlEconomic[] addlEconomic;
}
