package com.ibas.brta.vehims.acs;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    @JsonProperty ("access_token")
    String access_token; 
    @JsonProperty ("token_type")
    String token_type;//": "bearer",
    
    @JsonProperty ("expires_in")
    String expires_in;//": 599,
    
    @JsonProperty ("refresh_token")
    String refresh_token;//": "4743f93f040d4fef820e08d5e7567a4e",
    
    @JsonProperty ("client_id")
    String client_id;//": "EFDMS",

    @JsonProperty ("userName")
    String userName;//": "efdms_test_user",

    // @JsonProperty (".issued")
    // String issued;//": "Tue, 17 Oct 2023 04:47:02 GMT",

    // @JsonProperty (".expires")
    // String expires;//": "Tue, 17 Oct 2023 04:57:02 GMT",

    @JsonProperty ("responsecode")
    String responsecode;//": "0"
    
}
