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


}
