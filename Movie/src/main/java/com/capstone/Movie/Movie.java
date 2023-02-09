package com.capstone.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "mizan_filtered_movies_tmdb")
public class Movie {

    @Id
    @GeneratedValue
    private int movieid;
    private String title;
    private String genres;
    private int tmdbid;
    private String poster;
    private String description;
}
