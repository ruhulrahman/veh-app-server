package com.ibas.brta.vehims.userManagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Custom class to mark availability of the user during signup process.
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentityAvailability {
    private Boolean available;

}
