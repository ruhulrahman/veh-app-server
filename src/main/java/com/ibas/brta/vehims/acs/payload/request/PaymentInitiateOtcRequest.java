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
public class PaymentInitiateOtcRequest {
    @JsonProperty("paymentid")
    String paymentid; // ": "2D09181360429",

    @JsonProperty("paidamount")
    String paidamount; // 2940

    @JsonProperty("paymenttype")
    String paymenttype;// ": "1145101",

    @JsonProperty("bin")
    String bin;

    @JsonProperty("tin")
    String tin; // 754220222631

    @JsonProperty("email")
    String email;// ": "abc@xyz.com",

    @JsonProperty("mobile")
    String mobile;// ": "01763070743",

    @JsonProperty("organizationcode")
    String organizationcode;// ": "1190910000000",

    @JsonProperty("personcount")
    String personcount;// ": 10,

    @JsonProperty("pob_nid")
    String pob_nid;// ": "8701550140",

    @JsonProperty("pob_dob")
    String pob_dob;// ": "2021-08-26"

    @JsonProperty("organization_code")
    String organizationCode; // 1500301132187

    @JsonProperty("addleconomic")
    AddlEconomic[] addlEconomic;

}
