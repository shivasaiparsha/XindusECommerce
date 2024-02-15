package com.example.XindusEcommerce.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
public class Configuration {

    private  UserDetailsService userDetailsService ;


    public Configuration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // security filter chain authorize the user
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // role based authentication only one user presents
        return  http.csrf().disable() // disable the csrf filter, its not required for us now.mentioned full details about csrf filter in document in security section
                .httpBasic(Customizer.withDefaults())  // basic login option
                .authorizeHttpRequests(req->req
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/user/**").hasAnyAuthority( "user") // make the authorization according to our  requirements
                        .anyRequest().authenticated()) // any request authenticate

                .userDetailsService(userDetailsService).build();

    }

    @Bean // this bean is used to encode the password using BCryPasswordEncoder
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
