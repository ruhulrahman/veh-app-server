package com.ibas.brta.vehims.userManagement.controller;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.exception.AppException;
import com.ibas.brta.vehims.exception.ErrorDetails;
import com.ibas.brta.vehims.exception.TokenRefreshException;
import com.ibas.brta.vehims.userManagement.model.RefreshToken;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.common.model.rbac.Role;
import com.ibas.brta.vehims.common.model.rbac.RoleName;
import com.ibas.brta.vehims.userManagement.payload.request.LoginRequest;
import com.ibas.brta.vehims.userManagement.payload.request.SignupRequest;
import com.ibas.brta.vehims.userManagement.payload.request.TokenRefreshRequest;
import com.ibas.brta.vehims.userManagement.payload.request.NidSearchRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.userManagement.payload.response.AuthenticationResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserFullResponse;
import com.ibas.brta.vehims.userManagement.repository.RoleRepository;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import com.ibas.brta.vehims.security.JwtTokenProvider;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.userManagement.service.RefreshTokenService;
import com.ibas.brta.vehims.userManagement.service.UserService;

import jakarta.validation.Valid;

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

        @Autowired
        UserService userService;

        @PostMapping("/v1/login")
        @CrossOrigin(origins = "*")
        public ResponseEntity<?> authenticateUserV1(@Valid @RequestBody LoginRequest loginRequest) {

                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getUsernameOrEmail(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenProvider.generateToken(authentication);

                Map<String, Object> customArray = new HashMap<>();

                if (authentication != null && authentication.isAuthenticated()) {
                        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                        UserFullResponse userResponse = userService.getDataById(userPrincipal.getId());
                        customArray.put("userInfo", userResponse);
                }

                customArray.put("tokenInfo", new AuthenticationResponse(jwt));

                return ResponseEntity.ok(customArray);
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

//                user.setRoles(Collections.singleton(userRole));

                User result = userRepository.save(user);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/users/{username}")
                                .buildAndExpand(result.getUsername()).toUri();

                return ResponseEntity.created(location).body(ApiResponse.success("User created."));
        }
}
