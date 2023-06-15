package com.example.ec.explorecli.service;

import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.domain.TourRatingPK;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.repo.TourRatingRepository;
import com.example.ec.explorecli.repo.TourRepository;
import com.example.ec.explorecli.util.HelperMethods;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Created by Jahidul Arafat on 2023-
 * Sample RatingDTO object/bag
 * {
 *     "score":5,
 *     "comment": "Amazing tour",
 *     "customerId": 123
 * }
 */
@Service
@Data
@Transactional // This annotation ensures that the method executes as a single transaction, meaning that either all the database operations within the method will be committed together or rolled back if an exception occurs.
public class TourRatingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingService.class);
    private final TourRepository tourRepository;
    private final TourRatingRepository tourRatingRepository;
    private final HelperMethods helperMethods;

    // method to create a tour rating using tour id
    // For POST /tours/{tourId}/ratings  with RatingDTO as the JSON/payload in the Body
    public void createTourRating(Long tourId, @Validated RatingDto ratingDto){
        LOGGER.info("POST /tours/{}/ratings/{}",tourId,ratingDto);
        Tour tour = helperMethods.validateTour(tourId);
        tourRatingRepository.save(new TourRating(
                new TourRatingPK(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(),
                ratingDto.getComment()
                ));
    }

    // method to create a tour rating for a tour from a list of customer ids with tourId, score as path variable and customerIds request parameter
    // For POST /tours/{tourId}/ratings/{rating}?customers=1,2,3,4,5 with tourId, score as path variable and customerIds request parameter
    //@Transactional // This annotation ensures that the method executes as a single transaction, meaning that either all the database operations within the method will be committed together or rolled back if an exception occurs.
    public void createTourRatingByManyCustomers(Long tourId, Integer score, List<Integer> customerIds){
        LOGGER.info("POST /tours/{}/{}/customers/?customers={}", tourId, score,customerIds);
        Tour tour = helperMethods.validateTour(tourId);
        customerIds.stream()
                        .forEach(customerId -> {
                            LOGGER.debug("Attempt to create Tour Rating {} for customer {}", score, customerId);
                            tourRatingRepository.save(
                                            new TourRating(new TourRatingPK(tour, customerId),
                                                    score));
                                }

                        );
    }

    // method to get all tour ratings as RatingDTO of a specific tour
    // For GET /tours/{tourId}/ratings
    public List<RatingDto> getAllTourRatingsForTour(Long tourId){
        List<TourRating> tourRatings = tourRatingRepository.findByPkTourId(tourId);
        return tourRatings.stream()
              .map(RatingDto::new)
              .toList();
    }

    // method to get the average tour rating of a specific tour
    // For GET /tours/{tourId}/ratings/average
    public Double getAverageTourRatingForTour(Long tourId){
        helperMethods.validateTour(tourId);
        List<TourRating> tourRatings = tourRatingRepository.findByPkTourId(tourId);
        return tourRatings.stream()
              .mapToDouble(TourRating::getScore)
              .average()
              .orElse(0.0);
    }

    // method to update the score and comment of a specific tour rating
    public void updateTourRating(Long tourId, RatingDto ratingDto){
        // verify tour rating by tour and customer id
        TourRating tourRating = helperMethods.validateTourRating(tourId, ratingDto.getCustomerId());
        tourRating.setScore(ratingDto.getScore());
        tourRating.setComment(ratingDto.getComment());
        tourRatingRepository.save(tourRating);
    }

    // method to delete a specific tour rating by tour and customer id
    public void deleteTourRating(Long tourId, Integer customerId){
        TourRating tourRating = helperMethods.validateTourRating(tourId, customerId);
        System.out.println("Deleting tour rating with tour id " + tourId + " and customer id " + customerId);
        tourRatingRepository.delete(tourRating);
    }

    // method to get tour rating of a tour by customer id
    // For GET /tours/{tourId}/ratings/{customerId}
    public Integer getTourRatingByCustomer(Long tourId, Integer customerId){
        TourRating tourRating = helperMethods.validateTourRating(tourId, customerId);
        return tourRating.getScore();
    }






}