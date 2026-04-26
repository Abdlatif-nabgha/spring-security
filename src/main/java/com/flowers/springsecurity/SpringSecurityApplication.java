package com.flowers.springsecurity;

import com.flowers.springsecurity.entities.Role;
import com.flowers.springsecurity.entities.User;
import com.flowers.springsecurity.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountService service) {
        return args -> {
            service.addNewRole(new Role(null,"User"));
            service.addNewRole(new Role(null,"Admin"));
            service.addNewRole(new Role(null,"Manager"));

            service.addNewUser(new User(null, "user1", "1234", new HashSet<>()));
            service.addNewUser(new User(null, "user2", "1234", new HashSet<>()));
            service.addNewUser(new User(null, "user3", "1234", new HashSet<>()));
            service.addNewUser(new User(null, "admin", "1234", new HashSet<>()));

            service.addRoleToUser("user1", "User");
            service.addRoleToUser("user2", "User");
            service.addRoleToUser("user3", "Admin");
            service.addRoleToUser("admin", "Manager");


        };
    }
}
