package com.StarkIndustries.JwtAuthentication.Controller;

import com.StarkIndustries.JwtAuthentication.Models.Users;
import com.StarkIndustries.JwtAuthentication.Service.JwtService;
import com.StarkIndustries.JwtAuthentication.Service.MyUserDetailsService;
import com.StarkIndustries.JwtAuthentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    public UserService userService;

    @Autowired
    public JwtService jwtService;

    @GetMapping("/greetings")
    public String greetings(){
        return "Greetings\n I am Optimus Prime";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Users users){
        if(userService.signup(users)){
            System.out.println("username: "+users.getUsername()+" "+" password:"+users.getPassword());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Signup successfully!!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User already exist!!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users users){
        if(userService.login(users)){
            return ResponseEntity.of(Optional.of(jwtService.encodeUsername(users.getUsername())));
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to Login!!");
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);  // Extract the token from the header
            String username = jwtService.extractUserName(token);
            UserDetails userDetails = new MyUserDetailsService().loadUserByUsername(username);
            if (username != null && jwtService.validateToken(token,userDetails)) {
                return ResponseEntity.status(HttpStatus.OK).body("Token is valid for user: " + username);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers(){
        if(!userService.getUsers().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
