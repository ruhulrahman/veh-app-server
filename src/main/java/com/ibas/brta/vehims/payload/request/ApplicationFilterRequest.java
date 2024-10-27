package com.ibas.brta.vehims.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request payload to search registration applications
 * @author ashshakur.rahaman
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFilterRequest {
    String serviceRequestNo;
    String chassisNumber;
    String engineNumber;
    String nid;
    String mobile;
    String applicationDate;
}
