package com.capstone.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepo repo;

    //save movie into database
    public Movie saveMovie(Movie movie){
        return repo.save(movie);
    }
    //get movie by movieid
    public Movie getmoviebyId(int movieid)
    {
        return repo.findById(movieid).orElse(null);
    }

    //get all movies by movieid
    public List<Movie> getAllMoviesById(int movieid){
        return repo.findAllMoviesByMovieid(movieid);
    }
    //movies in the list
    public List<Movie> getMovies()
    {
        return repo.findAll();
    }
    //get movie by tmdbid
    public Movie getmoviebyTmdbid(int tmdbid){
        return repo.findByTmdbid(tmdbid);
    }
}
