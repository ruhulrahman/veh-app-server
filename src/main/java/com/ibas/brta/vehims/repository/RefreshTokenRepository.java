package com.ibas.brta.vehims.repository;

import com.ibas.brta.vehims.model.RefreshToken;
import com.ibas.brta.vehims.model.userManagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * To represent and manage the refresh tokens used to obtain new access tokens after the original access token has expired,
 * enabling users to maintain authenticated sessions.
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/19/24
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}