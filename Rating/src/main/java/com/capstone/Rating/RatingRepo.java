package com.capstone.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer> {

    Rating getByUseridAndMovieid(int userid, int movieid);

    List<Rating> getByUserid(int userid);
    List<Rating> getByMovieid(int movieid);

}
