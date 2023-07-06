package com.example.ec.explorecli.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * username - NotNull
 * password - NotNull
 *
 */

/**
 * Tips
 * It is always desired to use @Data for Getter, Setter and toString()
 * It is always desired to use @NoArgsConstructor for Default constructor
 * Use Constructor Injection to let Spring pick the Constructor based on its attributes
 */
@Data
@NoArgsConstructor // Default constructor // we dont need all argument constuctor here
public class LoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;


    private String firstName;
    private String lastName;

    // constructor with username and password
    // constructor injection, we didnt used @Autowired annotation here
    // let the spring pick the Constructor based on its attributes

    /**
     *
     * @param username
     * @param password
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
