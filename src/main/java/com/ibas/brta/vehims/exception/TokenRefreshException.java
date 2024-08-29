package com.ibas.brta.vehims.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * To handle and signal errors related to the refresh token process, such as invalid or expired tokens, during the
 * refresh token exchange or validation process
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/19/24
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}