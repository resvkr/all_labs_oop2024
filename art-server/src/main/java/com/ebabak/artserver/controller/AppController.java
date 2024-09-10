package com.ebabak.artserver.controller;

import com.ebabak.artserver.entity.User;
import com.ebabak.artserver.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class AppController {
    public final UserService userService;

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public User register() {
        return userService.register();
    }

    @GetMapping("tokens")
    public Set<Integer> getTokens() {
        return userService.getTokens();
    }
}
