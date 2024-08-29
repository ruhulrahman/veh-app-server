package com.ibas.brta.vehims.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Custom class to encapsulate the status of the API response, messages, and the actual data payload.
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T>{
    private boolean success;
    private String message;
    private T data;

    private Object error;

    // Factory methods for success responses
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message,null, null);
    }
    public static <T> ApiResponse<T> success(String message,T data) {
        return new ApiResponse(true, message,data, null);
    }
    // Factory methods for failure responses
    public static ApiResponse failure(String message, Object error) {
        return new ApiResponse(false, message, null, error);
    }

}
