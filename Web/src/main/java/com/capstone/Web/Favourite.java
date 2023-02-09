package com.capstone.Web;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Favourite {
    @Id
    @GeneratedValue
    private int favid; //primary key
    private int movieid; //indicate favourite
    private int userid;
}
