package com.capstone.Recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class RecommendRepo {

    //to use sql
    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Recommend> rowMapper = (ResultSet rs, int rowNum) -> {
        Recommend matrix = new Recommend();
        matrix.setMovieid(rs.getInt(1));

        return matrix;
    };

    //list out all movies with condition from database
    public List<Recommend> findAll(int movieid){
        String columnid = "ID_"+movieid;
        return jdbcTemplate.query("SELECT MOVIE_ID FROM PEARSONS_CORRELATIONS WHERE "+columnid+" > 0.5 AND MOVIE_ID <> "+movieid+" ORDER BY "+columnid+" DESC FETCH FIRST 5 ROWS ONLY ",rowMapper);
    }

}
