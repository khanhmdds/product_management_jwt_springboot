package com.example.ajax_boot_c08.controller;

import com.example.ajax_boot_c08.model.User;
import com.example.ajax_boot_c08.security.jwt.JwtResponse;
import com.example.ajax_boot_c08.security.jwt.JwtService;
import com.example.ajax_boot_c08.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<User> createNewAcc(@RequestBody User userAcc) {
////        Role role = roleRepository.findByName("ROLE_USER");
////        userAcc.setRole(role);
//        if (userService.save(userAcc))
//            return new ResponseEntity<>(userAcc, HttpStatus.OK);
//        else
//            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtService.generateTokenLogin(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User currentUser = userService.findByUsername(user.getUsername()).get();
//        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getUsername(), userDetails.getAuthorities()));
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername()).orElse(null);

        if (currentUser != null) {
            String redirectUrl = determineRedirectUrl(userDetails.getAuthorities());
            return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getUsername(), userDetails.getAuthorities(), redirectUrl));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    private String determineRedirectUrl(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return "index.html";
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                return "index2.html";
            }
        }
        return "/";
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
