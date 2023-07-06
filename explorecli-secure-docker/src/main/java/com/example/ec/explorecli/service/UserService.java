package com.example.ec.explorecli.service;

import com.example.ec.explorecli.repo.RoleRepository;
import com.example.ec.explorecli.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional // either all or none and rollback after completing the transaction
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    // UserService depends on userRepository and roleRepository
    // it also require AuthenticationManager and PasswordEncoder

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    // constructor injection
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
}
