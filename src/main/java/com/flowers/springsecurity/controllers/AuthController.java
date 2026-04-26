package com.flowers.springsecurity.controllers;

import com.flowers.springsecurity.entities.User;
import com.flowers.springsecurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return accountService.listUsers();
    }
}
