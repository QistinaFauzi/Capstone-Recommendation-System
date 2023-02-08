package com.capstone.Favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavouriteControl {

    @Autowired
    private FavouriteService service;

    //save favourite
    @PostMapping("/savefavourite")
    public Favourite saveFavourite(@RequestBody Favourite favourite){
        return service.saveFav(favourite);
    }


}
