package com.StarkIndustries.JwtAuthentication.Service;

import com.StarkIndustries.JwtAuthentication.Models.Users;
import com.StarkIndustries.JwtAuthentication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public Users findByUsername(String username){
        Users users = userRepository.findByUsername(username);
        if(userRepository.existsById(users.getUserId()))
            return users;
        return null;
    }

    public boolean signup(Users users){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

        if(userRepository.findByUsername(users.getUsername())==null){
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            userRepository.save(users);
            return true;
        }
        return false;
    }

    public boolean login(Users users){
        if(userRepository.findByUsername(users.getUsername())!=null)
            return true;
        return false;
    }

    public List<Users> getUsers(){
        return userRepository.findAll();
    }
}
