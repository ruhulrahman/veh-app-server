package com.ibas.brta.vehims.acs;

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
public class PaymentDetailsResponse {

    @JsonProperty("challan_no")
    String challan_no;
    @JsonProperty("tin")
    String tin;

    @JsonProperty("bin")
    String bin;

    @JsonProperty("payment_id")
    String payment_id;

    @JsonProperty("paidamount")
    String paidamount;

    @JsonProperty("entry_date")
    String entry_date;

    @JsonProperty("payment_date")

    String payment_date;
    @JsonProperty("client_name")

    String client_name;
    @JsonProperty("mobile_no")

    String mobile_no;
    @JsonProperty("email")
    String email; 
    @JsonProperty("address")
    String address; 
    @JsonProperty("bank_name")
    String bank_name;

    @JsonProperty("routing_no")
    String routing_no;

    @JsonProperty("used_flag")
    String used_flag;

    @JsonProperty("SubmissionID")
    String submissionId; 

}
