package com.ibas.brta.vehims.userManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * To model and persist refresh token data in the database, including details like token value, expiration date,
 * and associated user information for managing user sessions and authentication.
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/19/24
 */

@Data
@Accessors(chain = true)
@Entity(name = "x_f5tokens")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "RT_SEQ")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "auth_token")
    private String authToken;

    @Column(nullable = false)
    private Instant expiryDate;

}