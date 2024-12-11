package com.ibas.brta.vehims.userManagement.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ChangePasswordRequest {

    @NotBlank(message = "current Password cannot be blank")
    private String currentPassword;

    @NotBlank(message = "New Password cannot be blank")
    @Size(min = 8, max = 100)
    private String newPassword;

    @NotBlank(message = "Confirm Password cannot be blank")
    @Size(min = 8, max = 100)
    private String confirmPassword;
}
