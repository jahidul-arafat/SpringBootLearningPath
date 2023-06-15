package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.service.TourRatingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tours/{tourId}/ratings")
@AllArgsConstructor
public class TourRatingRestController {
    // add a logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingRestController.class);
    private final TourRatingService tourRatingService;
    // method to create a new tour rating
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable("tourId") Long tourId,
                                 @RequestBody @Validated RatingDto ratingDto) {
        LOGGER.info("POST /tour/{}/ratings/{}",tourId,ratingDto);
        tourRatingService.createTourRating(tourId, ratingDto);
    }

    // method to get all tour ratings for a tour
    @GetMapping
    public List<RatingDto> getAllTourRatingsForTour(@PathVariable("tourId") Long tourId) {
        LOGGER.info("GET /tour/{}/ratings", tourId);
        return tourRatingService.getAllTourRatingsForTour(tourId);
    }

    // method to get average tour rating for a tour
    @GetMapping("/average")
    public Double getAverageTourRatingForTour(@PathVariable("tourId") Long tourId) {
        LOGGER.info("GET /tour/{}/ratings/average", tourId);
        return tourRatingService.getAverageTourRatingForTour(tourId);
    }

    // method to update a tour rating
    @PutMapping
    public void updateTourRating(@PathVariable("tourId") Long tourId,
                                 @RequestBody @Validated RatingDto ratingDto) {
        // add logger info along with url parameter
        LOGGER.info("PUT /tour/{}/ratings/{}",tourId, ratingDto);
        tourRatingService.updateTourRating(tourId, ratingDto);
    }

    // method to delete a tour rating
    @DeleteMapping("/{customerId}")
    public void deleteTourRating(@PathVariable("tourId") Long tourId,
                                 @PathVariable("customerId") Integer customerId) {
        // add logger info along with url parameter
        LOGGER.info("DELETE /tour/{}/ratings/{}",tourId,customerId);
        tourRatingService.deleteTourRating(tourId, customerId);
    }

    // method to get tour rating by customer
    @GetMapping("/{customerId}")
    public Integer getTourRatingByCustomer(@PathVariable("tourId") Long tourId,
                                             @PathVariable("customerId") Integer customerId) {
        // add logger info with url parameter
        LOGGER.info("GET /tour/{}/ratings/{}", tourId,customerId);
        return tourRatingService.getTourRatingByCustomer(tourId, customerId);
    }

    // method to create a tour rating for a tour from a list of customer ids with tourId, score as path variable and customerIds request parameter
    @PostMapping("/{score}/customerIds")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRatingByManyCustomers(@PathVariable("tourId") Long tourId,
                                                 @PathVariable("score") Integer score,
                                                 @RequestParam("customerIds") @Validated List<Integer> customerIds) {
        // add logger info with url parameter
        LOGGER.info("POST /tour/{}/ratings/{}/customerIds/{}",tourId,score,customerIds);
        tourRatingService.createTourRatingByManyCustomers(tourId, score, customerIds);
    }

}
