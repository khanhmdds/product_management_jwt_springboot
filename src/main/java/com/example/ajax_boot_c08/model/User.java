//package com.example.ajax_boot_c08.model;
//
////import com.example.ajax_boot_c08.model.dto.UserAccDTO;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "users")
//public class User {
//    @Id
//    @GeneratedValue (strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String username;
//    private String password;
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles;
//
//    private String email;
//    private String fullName;
//    private String avatar;
//    private String coverPhoto;
//    private String Description;
//
//    public List<GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }
//
////    public UserAccDTO userAccDTO(){
////        return new UserAccDTO(id,username,email,fullName,avatar,coverPhoto,getDescription(),roles.stream().findFirst().orElse(null));
////    };
//}
