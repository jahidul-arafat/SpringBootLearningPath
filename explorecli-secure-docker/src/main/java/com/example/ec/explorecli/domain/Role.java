package com.example.ec.explorecli.domain;

import jakarta.persistence.*;
import lombok.Data;

/*
CREATE TABLE security_role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description varchar(100) DEFAULT NULL,
  role_name varchar(100) DEFAULT NULL
);
 */
@Entity
@Data
@Table(name = "security_role")
public class Role {
    private static final long serialVersionUID = 1L; // to avoid mismatching between serialization and deserialization
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "role_name")
    private String roleName;

}
