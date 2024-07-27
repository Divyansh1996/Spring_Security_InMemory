package com.example.spring_security_inmemory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

//    Basic Form Login code with login credentails coming from UserDetailsService using inMemoryDatabase
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()).authorizeRequests(request-> {
//            try {
//                request.anyRequest().authenticated().and().formLogin();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return http.build();
//    }

    // Code to check that /home, /index, will be accessed by all the users and only /about page will be accessed by admin User who has role of ADMIN and info for role is present in HomeController page.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(request->request.disable()).authorizeRequests(request-> {
            try {
                request.requestMatchers("/home","/index").permitAll().requestMatchers("/about").authenticated().and().formLogin();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withUsername("user").password(passwordEncoder().encode("userPassword")).roles("USER").build();
        UserDetails user2 = User.withUsername("admin").password(passwordEncoder().encode("adminPassword")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
