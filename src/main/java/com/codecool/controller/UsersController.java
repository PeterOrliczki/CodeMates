package com.codecool.controller;

import com.codecool.entity.Users;
import com.codecool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {


    @Autowired
    private UserRepository userRepository;

    @PutMapping(path = "/profile")
    public @ResponseBody
    void updateProfile(@RequestBody Users user) {
        Users userBU = userRepository.findByName(user.getName());

        if (!user.getEmail().equals(userBU.getEmail()) &&
                userRepository.findByEmail(user.getEmail()) != null) {
            throw new BadCredentialsException("New email address already exists!");

        }

        userBU.setAddress(user.getAddress());
        userBU.setPassword(user.getPassword());
        userBU.setLocation(user.getLocation());
        userBU.setEmail(user.getEmail());
        userBU.setRoles(user.getRoles());

        userRepository.save(userBU);

    }
}
