package com.codecool.controller;

import com.codecool.model.Users;
import com.codecool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class UsersController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @PutMapping(path = "/profile")
    public @ResponseBody
    void updateProfile(@RequestBody Users user) {
        Users userBU = userRepository.findByEmail(user.getEmail());

        userBU.setName(user.getName());
        userBU.setAddress(user.getAddress());
        userBU.setPassword(user.getPassword());
        userBU.setLocation(user.getLocation());
        userBU.setRoles(user.getRoles());

        userRepository.save(userBU);

    }
}