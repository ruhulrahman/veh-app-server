package com.ibas.brta.vehims.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * To encapsulate the JWT token and other relevant authentication details (like token expiration time or user information)
 * in a response format, returned after a successful authentication request.
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */
@Data
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = -1L;

    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;
    private long timestamp;
    @Value("${jwtExpirationInMs}")
    private int expiresIn;
    @Value("${jwtRefreshExpirationMs}")
    private int refreshExpiresIn;

    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
        this.timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        this.expiresIn = expiresIn;
    }

    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        this.expiresIn = refreshExpiresIn;
        this.refreshToken = refreshToken;
    }
}
