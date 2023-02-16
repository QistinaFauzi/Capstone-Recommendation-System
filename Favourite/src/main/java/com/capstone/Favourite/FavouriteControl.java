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

    //get favourite list from specific user
    @GetMapping("/getfavsbyuser/{userid}")
    public List<Favourite> getfavbyUserid(@PathVariable int userid){
        return service.getbyuserid(userid);
    }

    //delete a favourite by fav id
    @DeleteMapping("/deletefav/{favid}")
    public String deletefav(@PathVariable int favid){
        return service.deletebyid(favid);
    }
}
