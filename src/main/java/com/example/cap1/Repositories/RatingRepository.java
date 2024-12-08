package com.example.cap1.Repositories;

import com.example.cap1.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query("select R from Rating R where R.rating_id= :rating_id")
    Rating findRatingByRating_id(@Param("rating_id") Long rating_id);

    @Query("select R from Rating R where R.property_id= :property_id")
   List<Rating> findRatingByProperty_id(Long property_id);


    @Query("SELECT r FROM Rating r ORDER BY r.property_rating DESC")
    List<Rating> findTopRatedProperties();


    @Query("SELECT r FROM Rating r where r.property_rating between 1 and 2 ORDER BY r.property_rating DESC")
    List<Rating> findBadRatedProperties();


    @Query("SELECT r FROM Rating r where r.property_rating between 4 and 5 ORDER BY r.property_rating DESC")
    List<Rating> findTopNByOrderByPropertyRatingDesc();
}
