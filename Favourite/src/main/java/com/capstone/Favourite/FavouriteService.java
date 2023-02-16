package com.capstone.Favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService {
    @Autowired
    private FavouriteRepo repo;

    //save favourite movie
    public Favourite saveFav(Favourite favourite){
        return repo.save(favourite);
    }

    //get favourite by user id
    public List<Favourite> getbyuserid(int userid){
        return repo.findByUserid(userid);
    }

    //delete favourite by favid
    public String deletebyid(int favid){
        repo.deleteById(favid);
        return "Favourite id "+favid+" successfully deleted.";
    }
}
