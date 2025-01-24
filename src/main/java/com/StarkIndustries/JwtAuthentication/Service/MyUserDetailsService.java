package com.StarkIndustries.JwtAuthentication.Service;

import com.StarkIndustries.JwtAuthentication.Models.Users;
import com.StarkIndustries.JwtAuthentication.Models.UsersPrinciples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    public UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userService.findByUsername(username);
        return new UsersPrinciples(users);
    }
}
