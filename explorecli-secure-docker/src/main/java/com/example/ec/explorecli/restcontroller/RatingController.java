package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.dto.CustomerTourRefDto;
import com.example.ec.explorecli.dto.RatingAssembler;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.service.TourRatingService;
import com.example.ec.explorecli.util.HelperMethods;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Rating", description = "Rating API")
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
    @Operation(summary = "Get all ratings of all Tours irrespective to TourID and CustomerID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<RatingDto> getAllRatings() {
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();

        return tourRatings.stream()
                .map(ratingAssembler::toModel)
                .collect(Collectors.toList());
    }

    // method to get all ratings by TourRating score
    @GetMapping("/{score}")
    @Operation(summary = "Get all ratings of all Tours given TourRating score")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Rating not found")})
    public List<CustomerTourRefDto> getAllRatingsByScore(@PathVariable("score") Integer score) {
        List<TourRating> tourRatings = tourRatingService.getAllTourRatingsByScore(score);

        return tourRatings.stream()
               .map(ratingAssembler::toCustomerTourRefDto)
               .collect(Collectors.toList());
    }

    // method to rate a tour by tour id and customer id
    @PostMapping("/{score}/{tourId}/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Rate a tour given tour id, customer id and score")
    public RatingDto rateTour(@PathVariable("score") Integer score,
                               @PathVariable("tourId") Long tourId,
                               @PathVariable("customerId") Integer customerId) {
        return tourRatingService.createTourRatingByScore(tourId, score, customerId);
    }

    @PostMapping("/many/sequential/{score}/{tourId}/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Rate a List of tour given List of score and List of customerId")
    public void rateTourByManyScoreCustomer(@PathVariable("score") List<Integer> score,
                               @PathVariable("tourId") List<Long> tourId,
                               @PathVariable("customerId") List<Integer> customerId) {
        tourRatingService.createTourRatingsByListOfScoreTourCustomer(tourId, score, customerId);
    }

    @PostMapping("/many/collective/{tourId}/{score}/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Rate a List of tour given a Score and List of customerId")
    public void rateTourByManyScoreCustomerCollective(@PathVariable("score") Integer score,
                                            @PathVariable("tourId") List<Long> tourId,
                                            @PathVariable("customerId") List<Integer> customerId) {
        tourRatingService.createTourRatingsBYListOfScoreTourCustomerCollective(tourId,score,customerId);
    }



}
