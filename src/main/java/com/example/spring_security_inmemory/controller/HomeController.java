package com.example.spring_security_inmemory.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring")
public class HomeController {

    @RequestMapping("/index")
    public String Index(){
        return "Index Page";
    }

    @RequestMapping("/home")
    public String Home(){
        return "Home Page";
    }

    @RequestMapping("/about")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String About(){
        return "About Page";
    }
}
