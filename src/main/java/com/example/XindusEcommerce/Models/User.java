package com.example.XindusEcommerce.Models;

import com.example.XindusEcommerce.Security.UserDetailsService;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
         private Integer userid;

      @Column(unique = true, nullable = false)
         private  String username;

      @Column(unique = true, nullable = false)
      private String email;

      @Column(nullable = false)
         private String password;

      @Column(name = "role")
      private String role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
       private List<WishListItem> wishListItems=new ArrayList<>();

    public User(String username, String email, String password, String role) {
        this.username=username;
        this.email=email;
        this.password=password;
        this.role=role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(role));
        return authorityList;
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

    public User(String username, String password)
    {
        this.username=username;
        this.password=password;
    }
}
