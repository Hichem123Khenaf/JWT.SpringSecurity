package com.training.spring.security.jwtspringsecurity.ressource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRessource {

    @GetMapping("/home")
    public String getUser(){
        return "user works";
    }
}
