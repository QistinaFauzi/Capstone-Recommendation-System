package com.capstone.Favourite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepo extends JpaRepository<Favourite, Integer> {
    List<Favourite> findByUserid(int userid);
}
