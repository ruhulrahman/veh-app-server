package com.ibas.brta.vehims.acs.payload.request;

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
public class ACSPaymentInitiateRequest {
    @JsonProperty("paymentid")
    String paymentid;

    @JsonProperty("paidamount")
    String paidamount;

    @JsonProperty("id_type")
    String id_type;

    @JsonProperty("paymenttype")
    String paymenttype;

    @JsonProperty("organizationcode")
    String organizationcode;

    @JsonProperty("nid")
    String nid;

    @JsonProperty("dob")
    String dob;

    @JsonProperty("tin")
    String tin;

    @JsonProperty("anonymous_client_identity")
    String anonymous_client_identity;

    @JsonProperty("anonymous_client_name")
    String anonymous_client_name;

    @JsonProperty("anonymous_client_address")
    String anonymous_client_address;

    @JsonProperty("mobile")
    String mobile;
    @JsonProperty("AddlEconomic")
    AddlEconomic[] AddlEconomic;
}
