package com.pingme.ping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The PingApplication class is a Spring Boot application with a main method that starts the
 * application.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableWebMvc
public class PingApplication {
  public static void main(String[] args) {
    SpringApplication.run(PingApplication.class, args);
  }
}
