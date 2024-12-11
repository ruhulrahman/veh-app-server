package com.ibas.brta.vehims.userManagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NidSearchRequest {

    @NotBlank
    private String nid;

    @NotBlank
    private String dob;
    // private LocalDate dob;

    @NotBlank
    private String mobile;

}
