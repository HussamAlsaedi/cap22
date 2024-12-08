package com.example.cap1.Controllers;

import com.example.cap1.ApiResponse.ApiException;
import com.example.cap1.Models.Rating;
import com.example.cap1.Services.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/get")
    public List<Rating> getRatings() {
       return ratingService.findAllRatings();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiException> addRating(@RequestBody @Valid Rating rating, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().get(0).getDefaultMessage();
            return  ResponseEntity.status(400).body(new ApiException(message));
        }
         ratingService.addRating(rating);
        return  ResponseEntity.status(200).body(new ApiException("successfully added rating"));
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<ApiException> updateRating(@PathVariable Long ratingId, @RequestBody @Valid Rating rating, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().get(0).getDefaultMessage();
            return  ResponseEntity.status(400).body(new ApiException(message));
        }
        ratingService.updateRating(ratingId, rating);
        return  ResponseEntity.status(200).body(new ApiException("successfully updated rating"));
    }

    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<ApiException> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return  ResponseEntity.status(200).body(new ApiException("successfully deleted rating"));
    }

    //Endpoint  # 7
    // Get all ratings for a specific property
    @GetMapping("/property/{propertyId}")
    public List<Rating> getRatingsByProperty(@PathVariable Long propertyId) {

        return ratingService.getRatingsByProperty(propertyId);
    }

    //Endpoint  # 8
    // Get the top rated properties
    @GetMapping("/top-rated")
    public List<Rating> getTopRatedProperties() {
        return  ratingService.getTopRatedProperties();
    }

    //Endpoint  # 9
    // Get the bad rated properties
    @GetMapping("/bad-rated")
    public List<Rating> getBadRatedProperties() {
        return  ratingService.getBadRatedProperties();
    }



}
