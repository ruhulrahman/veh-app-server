package com.ibas.brta.vehims.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * Login info to be sent through API call for authentication.
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;


}
