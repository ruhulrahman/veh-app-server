package com.ibas.brta.vehims;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ashshakur.rahaman 08/19/24
 *         To provide the necessary configuration and setup for applications
 *         deployed as WAR files in external servlet containers.
 */
@SpringBootApplication
@EnableJpaAuditing
// @EnableScheduling // Enable scheduling functionality
public class ServletInitializer extends SpringBootServletInitializer {
    private final String devUrl = "localhost:8080";

    private final String prodUrl = "localhost:8080";

    @Autowired
    PasswordEncoder passwordEncoder;

    // Main driver method.
    public static void main(String[] args) {
        SpringApplication.run(ServletInitializer.class, args);

        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // String rawPassword = "123456"; // The plain text password
        // String encodedPassword = passwordEncoder.encode(rawPassword);
        //
        // System.out.println("Encoded password: " + encodedPassword);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        return application.sources(ServletInitializer.class);
    }

}
