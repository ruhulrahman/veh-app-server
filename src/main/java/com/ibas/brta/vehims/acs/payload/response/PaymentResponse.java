package com.ibas.brta.vehims.acs.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ashshakur.rahaman
 */
@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {
    String access_token;
    String challan_no;
    String paymentid;
    String paidamount;
    String transaction_id;
    String clientname;
    String responsecode;
    String bin;
    String tin;
    String serviceRequestNo;
    Long serviceId;
    String serviceCode;
    String serviceType;
}
