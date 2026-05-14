package com.marketgo.user.controller;

import com.marketgo.user.model.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private List<User> userDetails = new ArrayList<User>();

    @GetMapping("/users")
    public List<User> getUsers() {
        return userDetails;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        userDetails.add(user);
        return user ;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userDetails.get(id);
    }

}
