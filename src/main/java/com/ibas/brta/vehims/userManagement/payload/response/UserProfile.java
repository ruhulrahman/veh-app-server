package com.ibas.brta.vehims.userManagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * To provide comprehensive view of user's profile.
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial Version.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
}
