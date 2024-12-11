package com.ibas.brta.vehims.acs.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ashshakur.rahaman
 *
 */
@Getter
@Setter
@Data
@NoArgsConstructor
public class AddlEconomic {
    @JsonProperty("economic_code")
    String economic_code;
    @JsonProperty("organization_code")
    String organization_code;
    @JsonProperty("paidamount")
    String paidamount;
}
