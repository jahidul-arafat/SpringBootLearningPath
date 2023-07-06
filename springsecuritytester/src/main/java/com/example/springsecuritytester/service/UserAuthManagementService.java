package com.example.springsecuritytester.service;

import com.example.springsecuritytester.domain.UserInfo;
import com.example.springsecuritytester.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAuthManagementService {
    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    // Constructor injection
//    @Autowired
//    public UserAuthManagementService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
//        this.userInfoRepository = userInfoRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    // method to add a new user info into the UserInfoRepository
    public String addUserInfo(UserInfo userInfo) {
        // When saving the new user info into the UserInfoRepository, you must need to encrypt the password
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        // return a string with user name
        return "User: " + userInfo.getName() + " added successfully";
    }

    // method to get all the user info from the UserInfoRepository
    public List<UserInfo> getAllUserInfo() {
        return userInfoRepository.findAll();
    }
}
