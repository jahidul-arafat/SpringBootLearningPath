package com.example.ec.explorecli.service;

import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.domain.TourRatingPK;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.repo.TourRatingRepository;
import com.example.ec.explorecli.repo.TourRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class TourRatingService {
    private final TourRepository tourRepository;
    private final TourRatingRepository tourRatingRepository;

    // method to create a tour rating using tour id
    // For POST /tours/{tourId}/ratings  with RatingDTO as the JSON/payload in the Body
    public void createTourRating(Long tourId, RatingDto ratingDto){
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour with id " + tourId + " does not exist"));
        tourRatingRepository.save(new TourRating(
                new TourRatingPK(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(),
                ratingDto.getComment()
                ));
    }


}
