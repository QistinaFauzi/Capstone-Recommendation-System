package com.capstone.Rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingControl {

    @Autowired
    private RatingService service;

    //add new rating for specific user and movie
    @PostMapping("/addrating")
    public Rating addRating(@RequestBody Rating rating){
        return service.saveRating(rating);
    }

    //get the list of Rating from specific user
    @GetMapping("/userratings/{userid}")
    public List<Rating> getUserRating(@PathVariable int userid){
        return service.getRatingbyUserid(userid);
    }
    //get the list of Rating from specific movie
    @GetMapping("/movieraters/{movieid}")
    public List<Rating> getMovieRating(@PathVariable int movieid){
        return service.getRatingbyMovieid(movieid);
    }

    //get the rating for specific user and movie
    @GetMapping("/rated/{userid}/{movieid}")
    public Rating getRatingbyUseridandMovieid(@PathVariable("userid") int userid, @PathVariable("movieid") int movieid){
        return service.getRatingbyBothids(userid, movieid);
    }
}
