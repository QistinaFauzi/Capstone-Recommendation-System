package com.capstone.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieControl {

    @Autowired
    private MovieService service;

    //to get all movies
    @GetMapping("/allmovies")
    public List<Movie> findallMovies(){
        return service.getMovies();
    }

    //save movie
    @PostMapping("/addmovie")
    public Movie addMovie(@RequestBody Movie movies){
        return service.saveMovie(movies);
    }

    //delete movie
    /*@DeleteMapping("/deletemovie/{movieid}")
    public String deleteMovie(@PathVariable int movieid){
        return service.deleteMovie(movieid);
    }*/

    //get movie by movieid
    @GetMapping("/findbyid/{movieid}")
    public Movie findmoviebyId(@PathVariable int movieid){
        return service.getmoviebyId(movieid);
    }
    //get all movies by movieid
    @GetMapping("/findallmoviesbyid/{movieid}")
    public List<Movie> findallMoivesByid(@PathVariable int movieid){
        return service.getAllMoviesById(movieid);
    }
    //get movie by tmdbid
    @GetMapping("/findbytmdbid/{tmdbid}")
    public Movie findMoviebyTmdbid(@PathVariable int tmdbid) {
        return service.getmoviebyTmdbid(tmdbid);
    }
}
