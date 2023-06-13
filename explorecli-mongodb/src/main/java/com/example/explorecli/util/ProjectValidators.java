package com.example.explorecli.util;

import com.example.explorecli.domain.Tour;
import com.example.explorecli.domain.TourRating;
import com.example.explorecli.repositories.TourRatingRepository;
import com.example.explorecli.repositories.TourRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ProjectValidators {
    private final TourRepository tourRepository;
    private final TourRatingRepository tourRatingRepository;

    public ProjectValidators(TourRepository tourRepository, TourRatingRepository tourRatingRepository) {
        this.tourRepository = tourRepository;
        this.tourRatingRepository = tourRatingRepository;
    }
    // ------- Helper Methods ----------------
    // Helper Method-01
    // from 01 // this method must be private and should not be exposed outside
    // Verify the tourID and return the tourObject
    // throws NoSuchElementException if no tour found // Http 404
    public Tour verifyTour(String tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId)
                .orElseThrow(()-> new NoSuchElementException("Tour does not exists "+ tourId));
    }

    // Helper Method-02
    // Verify tourRating given tourId and customerId
    public TourRating verifyTourRating(String tourId, String customerId){
        return tourRatingRepository.findByTourIdAndCustomerId(tourId,customerId)
                .orElseThrow(()-> new NoSuchElementException(
                        String.format("Tour-Rating pair for request tourID: %s, for customerID: %s doesnt exist"
                                ,tourId
                                ,customerId)));

    }
}
