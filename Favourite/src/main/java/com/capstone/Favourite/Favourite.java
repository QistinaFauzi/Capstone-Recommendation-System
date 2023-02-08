package com.capstone.Favourite;

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
@Table(name="mizan_favourite")
public class Favourite {
    @Id
    @GeneratedValue
    private int favid; //primary key
    private int movieid; //indicate favourite
}
