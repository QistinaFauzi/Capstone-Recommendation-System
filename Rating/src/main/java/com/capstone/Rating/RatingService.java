package com.capstone.Rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepo repo;
    public Rating saveRating(Rating rating){
        return repo.save(rating);
    }


    //find a list of Ratings that for specific user. (Rate history)
    public List<Rating> getRatingbyUserid(int userid){
        return repo.getByUserid(userid);
    }

    //find a list of Ratings that for specific movie (can know all users that rate this movie)
    public List<Rating> getRatingbyMovieid(int movieid){
        return repo.getByMovieid(movieid);
    }

    //find a Rating that specific user and specific movie
    public Rating getRatingbyBothids(int userid, int movieid){
        return repo.getByUseridAndMovieid(userid, movieid);
    }
}
