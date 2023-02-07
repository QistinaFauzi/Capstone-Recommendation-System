package com.capstone.Recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendControl {
    @Autowired
    private RecommendService service;

    @GetMapping("/recommend/{movieid}")
    public List<Recommend> findAllMovies(@PathVariable int movieid){
        return service.findAll(movieid);
    }
}
