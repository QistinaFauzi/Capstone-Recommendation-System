package com.capstone.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControl {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User addUser(@RequestBody User user){
        return service.saveUser(user);
    }

    @GetMapping("/findbyname/{username}")
    public User findbyname(@PathVariable String username){
        return service.getByUsername(username);
    }
    /*@GetMapping("/findbytype/{type}")
    public User findbytype(@PathVariable String type){
        return service.getByType(type);
    }*/
}
