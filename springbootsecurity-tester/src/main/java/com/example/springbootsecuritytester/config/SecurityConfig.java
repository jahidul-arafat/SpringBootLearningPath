package com.example.springbootsecuritytester.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Notes:
 * @EnableMethodSecurity
 * Determines if Spring Security's PreAuthorize, PostAuthorize, PreFilter,
 * and PostFilter annotations should be enabled. Default is true.
 * Returns:
 * true if pre/post annotations should be enabled false otherwise
 *
 * We will be using PreAuthorize annotation to provide the method level security
 */

@Configuration // becomes a java bean configuration
@EnableMethodSecurity // to provide a method level security// @EnableGlobalMethodSecurity is deprecated in Spring Security 6 and Spring Boot 3.0
public class SecurityConfig {
    // create a security filter chain bean
    /*
    -   This is a method definition. The method takes an HttpSecurity instance as a parameter and
        returns a SecurityFilterChain instance.
    -   Within the securityFilterChain method, there is a security configuration setup:
            - http.csrf().disable(): CSRF (Cross-Site Request Forgery) is a type of attack that tricks
                the victim into submitting a malicious request. Here, CSRF protection is being disabled.
                In production environments, this is generally not recommended unless there's
                another CSRF protection mechanism in place.

            - http.authorizeHttpRequests((authorize)-> authorize.anyRequest().authenticated()):
                This line tells Spring Security to authorize all HTTP requests,
                and any request must be authenticated before it can be processed.

            - httpBasic(Customizer.withDefaults()): This enables HTTP Basic authentication,
                which is a simplistic protocol to send credentials in an HTTP header.
                Customizer.withDefaults() means it uses the default settings of HTTP Basic Authentication.

            - return http.build();: This builds the HttpSecurity into a SecurityFilterChain instance,
                which will be used by the Spring Security infrastructure.
     */


    // create a password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // disable csrf
        http.csrf().disable()
                .authorizeHttpRequests((authorize)-> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Creating inMemory users
    @Bean
    public UserDetailsService userDetailsService(){
        // create a user named "jahid" with password "jahid"
        // passwords cant be passed in plain text, if passed then 401 error will trigger
        // passwords must be encoded in base64
        UserDetails demo_user = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        UserDetails admin_user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        // return a list of users as in-memory users details service
        return new InMemoryUserDetailsManager(demo_user, admin_user);

    }
}
