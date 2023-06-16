package com.example.ec.explorecli.util;

import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.repo.TourRatingRepository;
import com.example.ec.explorecli.repo.TourRepository;
import org.springframework.stereotype.Component;

@Component
public class HelperMethods {
    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;

    public HelperMethods(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public Tour validateTour(Long tourId) {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour with id " + tourId + " does not exist"));
    }

    public TourRating validateTourRating(Long tourId, Integer customerId) {
        return tourRatingRepository.findByPkTourIdAndPkCustomerID(tourId, customerId)
                .orElseThrow(() -> new RuntimeException("TourRating with tour id " + tourId +
                        " and customer id " + customerId));
    }

//    public Integer validateScore(Integer score) {
//        if (score < 0 || score > 5) {
//            throw new RuntimeException("Score must be between 0 and 5");
//        }
//        return score;
//    }
}