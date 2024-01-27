package com.example.ajax_boot_c08.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String name;
    private String fullName;
    private String password;
    private Integer age;
    private String address;
    private String avatar;
    private String coverPhoto;
    private String Description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

//    public GrantedAuthority getAuthority() {
//        if (!roles.isEmpty()) {
//            Role firstRole = roles.iterator().next();
//            return new SimpleGrantedAuthority(firstRole.getName());
//        }
//        return new SimpleGrantedAuthority("ROLE_USER");
//    }

    public void setRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        } else {
            roles.clear(); // Đảm bảo chỉ có một vai trò được giữ trong tập hợp
        }
        roles.add(role);
    }
}
