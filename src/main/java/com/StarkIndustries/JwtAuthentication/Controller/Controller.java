package com.StarkIndustries.JwtAuthentication.Controller;

import com.StarkIndustries.JwtAuthentication.Models.Users;
import com.StarkIndustries.JwtAuthentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    public UserService userService;

    @GetMapping("/greetings")
    public String greetings(){
        return "Greetings\n I am Optimus Prime";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Users users){
        if(userService.signup(users))
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Signup successfully!!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User already exist!!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users users){
        if(userService.login(users))
            return ResponseEntity.status(HttpStatus.OK).body("User Login Successfully!!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to Login!!");
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers(){
        if(!userService.getUsers().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
