package com.example.ajax_boot_c08.controller;

import com.example.ajax_boot_c08.model.Customer;
//import com.example.ajax_boot_c08.model.dto.UserAccDTO;
import com.example.ajax_boot_c08.repository.ICustomerRepository;
import com.example.ajax_boot_c08.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class AccountController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/forgotPassword")
    public ResponseEntity<Customer> forgot(@RequestBody Customer userAcc) {
        Customer customer = customerService.findAllByUsernameAndEmail(userAcc.getUsername(), userAcc.getEmail());
        if (customer != null)
            return new ResponseEntity<>(customer, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/userAccDetail/{userAccId}")
    public ResponseEntity<Customer> findUserAccDTOById(@PathVariable Long userAccId) {
        Customer userAccDTO = customerService.findById(userAccId);
        if (userAccDTO != null) {
            return new ResponseEntity<>(userAccDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> createNewAcc(@RequestBody Customer userAcc) {
//        Role role = roleRepository.findByName("ROLE_USER");
//        userAcc.setRole(role);
        if (customerService.findAllByUsername(userAcc.getUsername()) == null) {
            userAcc.setAvatar("https://cdn.pixabay.com/photo/2014/03/24/13/49/avatar-294480_960_720.png");
            userAcc.setCoverPhoto("https://cdn.pixabay.com/photo/2014/03/24/13/49/avatar-294480_960_720.png");
            userAcc.setDescription("");
            return new ResponseEntity<>(userAcc, HttpStatus.OK);
        }
        else
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/editUserAcc/{userAccId}")
    public ResponseEntity<Customer> editUserAcc(@PathVariable Long userAccId,
                                                @RequestBody Customer userAcc) {
        Customer userAcc1 = customerService.findById(userAccId);
        if (userAcc1 != null) {
            userAcc.setId(userAccId);
//            userAcc.setUsername(userAcc1.getUsername());
            customerService.save(userAcc);
            return new ResponseEntity<>(userAcc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/editPassword/{userAccId}/{passworldNew}/{passwordOld}")
    public ResponseEntity<Customer> editPassword(@PathVariable String passworldNew,
                                                 @PathVariable Long userAccId,
                                                 @PathVariable String passwordOld) {
        Customer userAcc1 = customerService.findById(userAccId);
        if (userAcc1 != null) {
            if (userAcc1.getPassword().equals(passwordOld)) {
                customerService.changePassword(userAccId, passworldNew);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}