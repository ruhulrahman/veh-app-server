package com.ibas.brta.vehims.security;

import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * To load user-specific data
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/20/24
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        UserRepository userRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String usernameOrMobile)
                        throws UsernameNotFoundException {
                // Let people login with either username or mobile
                User user = userRepository.findByUsernameOrMobile(usernameOrMobile, usernameOrMobile)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "No User found with username or mobile : " + usernameOrMobile));

                return UserPrincipal.create(user);
        }

        @Transactional
        public UserDetails loadUserById(Long id) {
                User user = userRepository.findById(id).orElseThrow(
                                () -> new ResourceNotFoundException("User", "id", id));

                return UserPrincipal.create(user);
        }
}