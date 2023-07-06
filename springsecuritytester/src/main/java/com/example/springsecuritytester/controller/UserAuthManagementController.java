package com.example.springsecuritytester.controller;

import com.example.springsecuritytester.domain.UserInfo;
import com.example.springsecuritytester.service.UserAuthManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-auth-mgmt")
public class UserAuthManagementController {
    @Autowired
    private UserAuthManagementService userAuthManagementService;

    // POST method to register a new user
    @PostMapping("/register") // Any one can add a user
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user | Unrestricted")
    public String registerUser(@RequestBody UserInfo userInfo) {
        return userAuthManagementService.addUserInfo(userInfo);
    }

    // GET method to get all users
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all User Info || Restricted to ROLE_ADMIN")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<UserInfo> getAllUsers() {
        return userAuthManagementService.getAllUserInfo();
    }


}
