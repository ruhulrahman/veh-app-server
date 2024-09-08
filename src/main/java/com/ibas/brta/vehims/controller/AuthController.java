package com.ibas.brta.vehims.controller;

import com.ibas.brta.vehims.exception.AppException;
import com.ibas.brta.vehims.exception.ErrorDetails;
import com.ibas.brta.vehims.exception.TokenRefreshException;
import com.ibas.brta.vehims.model.RefreshToken;
import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.model.rbac.RoleName;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.payload.request.LoginRequest;
import com.ibas.brta.vehims.payload.request.SignupRequest;
import com.ibas.brta.vehims.payload.request.TokenRefreshRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.AuthenticationResponse;
import com.ibas.brta.vehims.repository.RoleRepository;
import com.ibas.brta.vehims.repository.UserRepository;
import com.ibas.brta.vehims.security.JwtTokenProvider;
import com.ibas.brta.vehims.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Collections;

/**
 * To handle HTTP requests related to user authentication (login) operations and
 * coordinating with the application's business logic.
 * 
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private static final Logger log = LoggerFactory.getLogger(AuthController.class);
        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        UserRepository userRepository;

        @Autowired
        RoleRepository roleRepository;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        JwtTokenProvider tokenProvider;

        @Autowired
        RefreshTokenService refreshTokenService;

        @PostMapping("/v1/login")
        @CrossOrigin(origins = "*")
        public ResponseEntity<?> authenticateUserV1(@Valid @RequestBody LoginRequest loginRequest) {

                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getUsernameOrEmail(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenProvider.generateToken(authentication);
                return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }

        @PostMapping("/v1/rt")
        public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
                String requestRefreshToken = request.getRefreshToken();
                // logger.error("Getting Refresh token for:" + requestRefreshToken);

                return refreshTokenService.findByToken(requestRefreshToken)
                                .map(refreshTokenService::verifyExpiration)
                                .map(RefreshToken::getUser)
                                .map(user -> {
                                        String token = tokenProvider.generateTokenFromUsername(user.getUsername());
                                        return ResponseEntity
                                                        .ok(new AuthenticationResponse(token, requestRefreshToken));
                                })
                                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                                                HttpStatus.NO_CONTENT.getReasonPhrase()));
        }

        @PostMapping("/v1/signup")
        public ResponseEntity<?> registerUserV1(@Valid @RequestBody SignupRequest signupRequest) {

                log.error("Entering method Register user:" + signupRequest.getUsername());

                if (userRepository.existsByUsername(signupRequest.getUsername())) {
                        return new ResponseEntity(
                                        ApiResponse.failure("Signup failed.",
                                                        new ErrorDetails("CONFLICT",
                                                                        "A user with this username already exists.")),
                                        HttpStatus.BAD_REQUEST);
                }

                if (userRepository.existsByEmail(signupRequest.getEmail())) {
                        return new ResponseEntity(
                                        ApiResponse.failure("Signup failed.",
                                                        new ErrorDetails("CONFLICT",
                                                                        "A user with this email already exists.")),
                                        HttpStatus.BAD_REQUEST);
                }

                // Creating user's account
                User user = new User(signupRequest.getNameEn(), signupRequest.getNameBn(), signupRequest.getUsername(),
                                signupRequest.getEmail(), signupRequest.getMobile(), signupRequest.getPassword(),
                                Long.parseLong(signupRequest.getDesignationId()), Boolean.FALSE, Boolean.TRUE);

                user.setPassword(passwordEncoder.encode(user.getPassword()));

                Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new AppException("User Role not set."));

                user.setRoles(Collections.singleton(userRole));

                User result = userRepository.save(user);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/users/{username}")
                                .buildAndExpand(result.getUsername()).toUri();

                return ResponseEntity.created(location).body(ApiResponse.success("User created."));
        }
}
