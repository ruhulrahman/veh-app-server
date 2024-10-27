package com.ibas.brta.vehims.acs;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ashshakur.rahaman
 */

@Setter
@Getter
@NoArgsConstructor
public class PaymentInitiateResponse {

    @JsonProperty("responsecode")
    String responsecode;

    @JsonProperty("transaction_id")
    String transaction_id;

    @JsonProperty("responseurl")
    String responseurl;

    @JsonProperty ("message")
    String message; 

    String bearertoken; 
    
    //http://training.finance.gov.bd/acs/general/payments?transaction_id=ddITYc5W4vJ78jG2mgwpjyekWbkwfJDKs4ZyRqyZUtyFnXjX
}
