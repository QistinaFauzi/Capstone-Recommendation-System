package com.capstone.Rating;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="mizan_userrating")
public class Rating {
    @Id
    @GeneratedValue
    private int ratingid;
    private int movieid;
    private double rating;
    private int userid;
}
