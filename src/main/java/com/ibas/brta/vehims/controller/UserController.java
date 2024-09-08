package com.ibas.brta.vehims.controller;

import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.payload.UserIdentityAvailability;
import com.ibas.brta.vehims.payload.UserProfile;
import com.ibas.brta.vehims.payload.UserSummary;
import com.ibas.brta.vehims.repository.UserRepository;
import com.ibas.brta.vehims.security.CurrentUser;
import com.ibas.brta.vehims.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * To handle HTTP requests related to user operations and coordinating with the
 * application's business logic.
 * 
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
                currentUser.getNameEn(), currentUser.getNameBn(), currentUser.getEmail());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getNameEn(),
                user.getCreatedAt());

        return userProfile;
    }

}
