package com.ibas.brta.vehims.payload.request;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}