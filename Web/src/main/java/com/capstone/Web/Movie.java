package com.capstone.Web;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {

    @Id
    @GeneratedValue
    private int movieid;
    private String title;
    private String genres;
    private int tmdbid;
    private String poster;
}
