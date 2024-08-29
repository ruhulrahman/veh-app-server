package com.ibas.brta.vehims.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request payload for Signup info.
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 11, max = 13)
    private String mobile;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    private String altName;

    @NotBlank
    private String designationId;

}
