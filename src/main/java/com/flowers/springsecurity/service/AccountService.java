package com.flowers.springsecurity.service;


import com.flowers.springsecurity.entities.Role;
import com.flowers.springsecurity.entities.User;

import java.util.List;

public interface AccountService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    void addRoleToUser(String username, String roleName);
    User loadUserByUsername(String username);
    List<User> listUsers();
}
