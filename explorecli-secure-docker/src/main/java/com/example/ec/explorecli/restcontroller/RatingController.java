package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.dto.CustomerTourRefDto;
import com.example.ec.explorecli.dto.RatingAssembler;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.service.TourRatingService;
import com.example.ec.explorecli.util.HelperMethods;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
In general, it is recommended to use @Transactional at the service layer rather than the controller layer.
The service layer is responsible for handling business logic and interacting with the data layer,
making it a suitable place to manage transactions.
 */
@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingAssembler ratingAssembler;
    private final TourRatingService tourRatingService;
    private final HelperMethods helperMethods;

    public RatingController(RatingAssembler ratingAssembler, TourRatingService tourRatingService, HelperMethods helperMethods) {
        this.ratingAssembler = ratingAssembler;
        this.tourRatingService = tourRatingService;
        this.helperMethods = helperMethods;
    }

    @GetMapping
    public List<RatingDto> getAllRatings() {
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();

        return tourRatings.stream()
                .map(ratingAssembler::toModel)
                .collect(Collectors.toList());
    }

    // method to get all ratings by TourRating score
    @GetMapping("/{score}")
    public List<CustomerTourRefDto> getAllRatingsByScore(@PathVariable("score") Integer score) {
        List<TourRating> tourRatings = tourRatingService.getAllTourRatingsByScore(score);

        return tourRatings.stream()
               .map(ratingAssembler::toCustomerTourRefDto)
               .collect(Collectors.toList());
    }

    // method to rate a tour by tour id and customer id
    @PostMapping("/{score}/{tourId}/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto rateTour(@PathVariable("score") Integer score,
                               @PathVariable("tourId") Long tourId,
                               @PathVariable("customerId") Integer customerId) {
        return tourRatingService.createTourRatingByScore(tourId, score, customerId);
    }

    @PostMapping("/many/sequential/{score}/{tourId}/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void rateTourByManyScoreCustomer(@PathVariable("score") List<Integer> score,
                               @PathVariable("tourId") List<Long> tourId,
                               @PathVariable("customerId") List<Integer> customerId) {
        tourRatingService.createTourRatingsByListOfScoreTourCustomer(tourId, score, customerId);
    }

    @PostMapping("/many/collective/{tourId}/{score}/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void rateTourByManyScoreCustomerCollective(@PathVariable("score") Integer score,
                                            @PathVariable("tourId") List<Long> tourId,
                                            @PathVariable("customerId") List<Integer> customerId) {
        tourRatingService.createTourRatingsBYListOfScoreTourCustomerCollective(tourId,score,customerId);
    }



}
