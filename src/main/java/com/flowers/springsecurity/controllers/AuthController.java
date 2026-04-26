package com.flowers.springsecurity.controllers;

import com.flowers.springsecurity.entities.Role;
import com.flowers.springsecurity.entities.User;
import com.flowers.springsecurity.service.AccountService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return accountService.addNewUser(user);
    }

    @PostMapping("/roles")
    public Role addNewRole(@RequestBody Role role) {
        return accountService.addNewRole(role);
    }

    @PostMapping("/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUser roleUser) {
        accountService.addRoleToUser(roleUser.getUsername(), roleUser.getRoleName());
    }
}

@Data
class RoleUser{
    private String username;
    private String roleName;
}