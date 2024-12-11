package com.ibas.brta.vehims.userManagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * To encapsulate a summary of a user's information
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial Version
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private String nameEn;
    private String nameBn;
    private String email;
}
