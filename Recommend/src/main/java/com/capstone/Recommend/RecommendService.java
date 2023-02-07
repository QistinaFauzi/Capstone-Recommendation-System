package com.capstone.Recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendService {
    @Autowired
    private RecommendRepo repo;

    public List<Recommend> findAll(int movieid){
        return repo.findAll(movieid);
    }
}
