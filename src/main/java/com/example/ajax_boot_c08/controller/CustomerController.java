package com.example.ajax_boot_c08.controller;

import com.example.ajax_boot_c08.model.Customer;
//import com.example.ajax_boot_c08.model.dto.UserAccDTO;
import com.example.ajax_boot_c08.model.Role;
import com.example.ajax_boot_c08.repository.ICustomerRepository;
import com.example.ajax_boot_c08.repository.IRoleRepository;
import com.example.ajax_boot_c08.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin("*")
@RequestMapping("/customers")
@PropertySource("classpath:application.properties")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private IRoleRepository roleRepository;

    @Value("${upload.path}")
    private String link;

    @Value("${display.path}")
    private String displayLink;

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> findAll(@PageableDefault(size = 2) Pageable pageable) {
        return new ResponseEntity<>(customerService.findAllPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> findAll(@RequestParam("search") String search) {
        return new ResponseEntity<>(customerService.findAllByName(search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if ((customerService.findAllByUsername(customer.getUsername()) == null) && (customerService.findAllByEmail(customer.getEmail()) == null)) {
            Role role = roleRepository.findByName("ROLE_USER");
            customer.setRole(role);
//            String generatedPassword = generateRandomString(8);
            return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
        }
        else
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@RequestBody Customer customer,
                                           @PathVariable Long id) {
        Customer customerUpdated = customerService.findById(id);
        if (customerUpdated != null) {
            Role role = roleRepository.findByName("ROLE_USER");
            customer.setRole(role);
            customer.setAvatar("https://cdn.pixabay.com/photo/2014/03/24/13/49/avatar-294480_960_720.png");
            return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable Long id) {
//        customerService.delete(id);
//        return new ResponseEntity<>("Delete done!", HttpStatus.OK);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id.equals(2L)) {
            // Trả về lỗi nếu id là 2
            return new ResponseEntity<>("Cannot delete Admin Account", HttpStatus.BAD_REQUEST);
        }
        customerService.delete(id);
        return new ResponseEntity<>("Delete done!", HttpStatus.OK);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<Customer> createUpload(@RequestPart(value = "file", required = false) MultipartFile file,
                                                 @RequestPart("customer") Customer customer) {
        if ((customerService.findAllByUsername(customer.getUsername()) == null) && (customerService.findAllByEmail(customer.getEmail()) == null)) {

            if (file != null) {
                String fileName = file.getOriginalFilename();
                try {
                    FileCopyUtils.copy(file.getBytes(), new File(link + fileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                customer.setAvatar(displayLink + fileName);
//            customer.setAvatar("https://cdn.pixabay.com/photo/2014/03/24/13/49/avatar-294480_960_720.png");
            } else {
//            customer.setAvatar(displayLink + "imgavt.jpg");
                customer.setAvatar("https://cdn.pixabay.com/photo/2014/03/24/13/49/avatar-294480_960_720.png");
            }
            Role role = roleRepository.findByName("ROLE_USER");
            customer.setRole(role);
            return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/upload1")
    public ResponseEntity<?> createUpload1(@RequestPart("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
