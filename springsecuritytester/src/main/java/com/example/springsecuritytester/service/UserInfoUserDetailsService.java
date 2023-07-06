package com.example.springsecuritytester.service;

import com.example.springsecuritytester.domain.UserInfo;
import com.example.springsecuritytester.dto.UserInfoUserDetails;
import com.example.springsecuritytester.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * What if constructor-based dependency injection is used instead of @Autowired?
 * @Service
 * @Transactional
 * @AllArgsConstructor
 * @NoArgsConstructor
 * public class UserInfoUserDetailsService implements UserDetailsService {
 *     private UserInfoRepository userInfoRepository;
 *     // ...
 * }
 * In this code snippet, the @Autowired annotation is not used explicitly. Instead, the UserInfoRepository dependency is injected through constructor-based dependency injection using the @AllArgsConstructor annotation. The @AllArgsConstructor annotation generates a constructor with parameters for all the fields in the class, and the UserInfoRepository is automatically injected when the UserInfoUserDetailsService instance is created. The @NoArgsConstructor annotation generates a no-argument constructor.
 */
@Service
@Transactional // this should be in Service layer, not in the controller layer
//@AllArgsConstructor
//@NoArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired // means that the UserInfoRepository bean will be automatically instantiated and injected into the UserInfoUserDetailsService class by the Spring framework.
    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // you can get this user details from the UserInfoRepository
        // that's why you have to inject this UserInfoRepository
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);

        // Convert the UserInfo to UserDetails and return it
        // that's why we designed the DTO UserInfoUserDetails
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
