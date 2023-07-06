package com.example.springsecuritytester.config;

import com.example.springsecuritytester.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

/**
 * @EnableWebSecurity enables the web security module in Spring Security and
 * makes it possible to use method-level security via @EnableMethodSecurity.
 */

/**
 * In a Spring Boot application, the @EnableWebSecurity annotation is not strictly necessary if you have a SecurityConfig class that extends WebSecurityConfigurerAdapter (or simply implements SecurityConfigurerAdapter<SecurityFilterChain, HttpSecurity> in newer versions of Spring Security), because Spring Boot's auto-configuration feature automatically applies security to the application.
 */
@Configuration // becomes a java bean configuration
@EnableWebSecurity
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

    // these not required anymore, as we gonna create UserInfo through UserAuthManagementService through
    // api endpoint http://localhost:8080/user-auth-mgmt/register
//    @Value("${user.user1.password}") // this is imported from resources/application-secret.properties
//    private String user1Password;
//
//    @Value("${user.admin.password}") // this is imported from resources/application-secret.properties
//    private String adminPassword;

    // create a password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Without this AuthenticationProvider, you cant authenticate as a user or admin in formBased login
    // Create a AuthenticationProvider bean

    /**
     *  the code sets up and configures an instance of DaoAuthenticationProvider as an AuthenticationProvider bean. The DaoAuthenticationProvider is responsible for authenticating users based on the provided user details and password encoding. The userDetailsService() and passwordEncoder() methods are likely defined elsewhere in the configuration or application, providing the necessary implementations for the UserDetailsService and PasswordEncoder interfaces.
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // define the authorization configurations
        http.csrf().disable(); // dont do it in production, unless you have another CSRF protection mechanism in place
        http.authorizeHttpRequests()
                .requestMatchers(
                        "/products/welcome",    // Allow everyone to access the welcome page
                        "/user-auth-mgmt/register",    // Allow anyone to add a new user
                        "/swagger-ui/**",           // Allow all under swagger-ui path
                        "/v3/api-docs/**",          // Allow all under v3/api-docs path
                        "/api-docs/**",              // Allow all under api-docs path
                        "/api-docs",
                        "/swagger-ui.html",
                        "/webjars/**",              // Allow webjars
                        "/swagger-resources/**",    // Allow swagger resources
                        "/configuration/**",        // Allow all under configuration path
                        "/v2/api-docs",             // For Swagger2 documentation
                        "/v3/api-docs/swagger-config"
                )
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/products/**","/user-auth-mgmt/**")
                .authenticated()
                .and()
                .formLogin() // or you can even try .httpBasic() instead of.formLogin()
        ;
        return http.build();

        // define the authorization configurations
        // define the authorization configurations
//        http.csrf(csrf -> csrf.disable());
//        http.authorizeRequests(authorize -> {
//            authorize.requestMatchers("/products/welcome", "/products/new",
//                    "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/api-docs", "/swagger-ui.html",
//                    "/webjars/**", "/swagger-resources/**", "/configuration/**", "/v2/api-docs",
//                    "/v3/api-docs/swagger-config").permitAll();
//            authorize.requestMatchers("/products/**").authenticated();
//        });
//        return http.build();

    }

    // Creating inMemory users
    // This is even not a good practice to hardcode the userdetails in the code itself
    // Good Practice: Load these user details from database
    // Solution: Create a user details entity
    @Bean
    public UserDetailsService userDetailsService(){
        // define the authentication configurations

        // create a user named "jahid" with password "jahid"
        // passwords cant be passed in plain text, if passed then 401 error will trigger
        // passwords must be encoded in base64
//        UserDetails demo_user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode(user1Password))
//                .roles("USER","ADMIN","HR") // user1 can be an ADMIN or HR or a normal USER
//                .build();
//
//        UserDetails admin_user = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode(adminPassword))
//                .roles("ADMIN")
//                .build();

        // Since we are not connecting to database to load the users, we have to load the users inMemory
        // return a list of users as in-memory users details service
//        return new InMemoryUserDetailsManager(demo_user, admin_user);

        return new UserInfoUserDetailsService(); // you have to implement 'UserDetailsService' method 'loadUserByUsername(String username)'
                                                // and return you the UserDetails, but we have the UserInfo object.
                                                // That's why we created a DTO to convert the UserInfo object to UserDetails object.

    }
}
