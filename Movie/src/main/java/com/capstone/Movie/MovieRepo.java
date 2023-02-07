package com.capstone.Movie;
import com.capstone.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {
    Movie findByTmdbid(int tmdbid);
    List<Movie> findAllMoviesByMovieid(int movieid);
}
