package com.capstone.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public User saveUser(User user){
        return repo.save(user);
    }

    public User getByUsername(String username){
        return repo.findByUsername(username);
    }

    public User getByType(String type){
        return repo.findByType(type);
    }
}
