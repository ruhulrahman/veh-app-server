package com.ibas.brta.vehims.configurations.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExamCenterRequest {
    private Long id;

    @NotNull(message = "Organization Id cannot be null")
    private Long orgId;

    @NotNull(message = "Thana Id cannot be null")
    private Long thanaId;

    @NotNull(message = "Address in English cannot be null")
    @Size(max = 100)
    private String addressEn;

    @NotNull(message = "Address in Bangla cannot be null")
    @Size(max = 100)
    private String addressBn;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
