package com.example.ec.explorecli.repo;

import com.example.ec.explorecli.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/*
- The @Repository annotation is used to indicate that the interface is a repository, which is responsible for performing database operations related to the User entity.
- The @RepositoryRestResource annotation is used to customize the RESTful resource exported by the repository. In this case, exported = true indicates that the repository should be exposed as a RESTful resource, allowing clients to perform CRUD (Create, Read, Update, Delete) operations on the User entity.
 */
@Repository
@RepositoryRestResource(exported = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
