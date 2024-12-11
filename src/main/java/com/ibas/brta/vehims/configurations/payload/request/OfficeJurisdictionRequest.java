package com.ibas.brta.vehims.configurations.payload.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OfficeJurisdictionRequest {
    // private Long id;

    @NotNull(message = "Organization Id cannot be null")
    private Long orgId;

    @NotNull(message = "Thana Ids cannot be null")
    private List<Long> thanaIds;

    // @NotNull(message = "Thana Id cannot be null")
    // private Long thanaId;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
