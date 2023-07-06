package com.example.ec.explorecli.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Arrays;
import java.util.List;

/*
CREATE TABLE security_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL
);
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "security_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    /*
    why @JsonIgnore ?
    - it ensures that the password will not be included in the JSON representation of the entity
    when it is returned in an API response, for example.
    - This helps to prevent exposing sensitive data and enhances security by ensuring that the password
    is not unintentionally leaked or exposed to unauthorized users.

     */
    @Column(name = "password")
    @JsonIgnore //  to indicate that the password field should be ignored during the serialization and deserialization process of the associated entity (e.g., when converting the entity to JSON format).
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /*
    CREATE TABLE user_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT FK_SECURITY_USER_ID FOREIGN KEY (user_id) REFERENCES security_user (id),
  CONSTRAINT FK_SECURITY_ROLE_ID FOREIGN KEY (role_id) REFERENCES security_role (id)
);
     */

    /*
    Table<user>
    | id | username | password |
    |----|----------|----------|
    | 1  | user1    | password1|
    | 2  | user2    | password2|

    Table<role>
    | id | role_name | description |
    |----|-----------|-------------|
    | 1  | ROLE_ADMIN| Admin role  |
    | 2  | ROLE_USER | User role   |

    Table<user_role>
    | user_id | role_id |
    |---------|---------|
    | 1       | 1       |
    | 1       | 2       |
    | 2       | 2       |
     */

    // a many-to-many relationship mapping between the User entity and the Role entity using JPA annotations.
    // This annotation indicates a many-to-many relationship between the User entity and the Role entity.
    // This attribute specifies the foreign key column in the join table that references the primary key column of the inverse entity (Role).
    // Means, multiple users can have one or more roles.
    // The EAGER fetch type specifies that the associated roles should be fetched eagerly along with the user entity whenever the user is retrieved from the database.
    // FK: joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") --> it indicates that the "user_id" column in the join table references the "id" column of the "User" entity.
    // FK: joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") --> it indicates that the "role_id" column in the join table references the "id"
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    // constructor
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.roles = Arrays.asList(role);

    }

}
