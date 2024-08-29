package com.ibas.brta.vehims;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



import java.util.TimeZone;


/**
 * @author ashshakur.rahaman 08/19/24
 * To provide the necessary configuration and setup for applications deployed as WAR files in external servlet containers.
 */
@Configuration
@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.ibas.brta.vehims.*"})

public class ServletInitializer extends SpringBootServletInitializer {
    private  final String devUrl ="localhost:8080";

    private final String prodUrl="localhost:8080";
    // Main driver method.
    public static void main(String[] args) {
        SpringApplication.run(ServletInitializer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        return application.sources(ServletInitializer.class);
    }

}
