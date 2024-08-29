package com.ibas.brta.vehims.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * To encapsulate error-related information with API response
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetails {
    private String code;
    private String message;
}
