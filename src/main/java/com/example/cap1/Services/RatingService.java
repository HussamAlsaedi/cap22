package com.example.cap1.Services;

import com.example.cap1.ApiResponse.ApiException;
import com.example.cap1.Models.Property;
import com.example.cap1.Models.Rating;
import com.example.cap1.Models.Tenant;
import com.example.cap1.Repositories.PropertyRepository;
import com.example.cap1.Repositories.RatingRepository;
import com.example.cap1.Repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private TenantRepository tenantRepository;





    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }


    public void addRating(Rating rating) {
        Tenant tenant = tenantRepository.findTenantById(rating.getTenant_id());
        if (tenant == null) {
            throw new ApiException("Tenant not found");
        }

        Property property = propertyRepository.findPropertyById(rating.getProperty_id());
        if (property == null) {
            throw new ApiException("Property id not found");
        }

        ratingRepository.save(rating);
    }

    public void updateRating(Long rating_id, Rating rating) {
        Rating oldRating = ratingRepository.findRatingByRating_id(rating_id);
        if (oldRating == null) {
            throw new ApiException("Rating not found");
        }

        oldRating.setTenant_id(rating.getTenant_id());
        oldRating.setProperty_id(rating.getProperty_id());
        oldRating.setProperty_rating(rating.getProperty_rating());
        oldRating.setDescription(rating.getDescription());
        ratingRepository.save(oldRating);
    }

    public void deleteRating(Long rating_id) {
        Rating rating = ratingRepository.findRatingByRating_id(rating_id);

        if (rating == null) {
            throw new RuntimeException("contract not found");
        }

        ratingRepository.delete(rating);
    }

    public List<Rating> getRatingsByProperty(Long propertyId) {
        List<Rating> ratings = ratingRepository.findRatingByProperty_id(propertyId);
        return ratings;
    }

    public List<Rating> getTopRatedProperties() {
        return ratingRepository.findTopNByOrderByPropertyRatingDesc();
    }

    public List<Rating> getBadRatedProperties() {
        return ratingRepository.findBadRatedProperties();
    }
}
