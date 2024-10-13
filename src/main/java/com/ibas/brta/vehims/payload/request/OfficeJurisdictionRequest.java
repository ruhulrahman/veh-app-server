package com.ibas.brta.vehims.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OfficeJurisdictionRequest {
    private Long id;

    @NotNull(message = "Organization Id cannot be null")
    private Long orgId;

    @NotNull(message = "Thana Id cannot be null")
    private Long thanaId;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
