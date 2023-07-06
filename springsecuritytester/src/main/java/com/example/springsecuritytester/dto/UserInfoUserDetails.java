package com.example.springsecuritytester.dto;

import com.example.springsecuritytester.domain.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities; // GrantedAuthority is an interface,

    // Constructor
    // Convert the user info into a user details object as understand by spring security
    public UserInfoUserDetails(UserInfo userInfo) {
        this.username = userInfo.getName();
        this.password = userInfo.getPassword();
        // you can assign multiple roles to a single user
        // i.e. user 'John' has role 'ADMIN', 'HR' and 'USER'
        // so, from the UserInfo object, we will fetch the roles from the UserInfo object, splict by comma,
        // and then map them to GrantedAuthority objects (SimpleGrantedAuthority implements interface GrantedAuthority )
        // and finally add them to the list of authorities
        this.authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
