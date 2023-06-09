package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.service.TourRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/*
In general, it is recommended to use @Transactional at the service layer rather than the controller layer.
The service layer is responsible for handling business logic and interacting with the data layer,
making it a suitable place to manage transactions.
 */

@RestController
@RequestMapping("/tours/{tourId}/ratings")
@AllArgsConstructor
@Tag(name = "Tour Rating", description = "This is the Tour Rating API")
public class TourRatingRestController {
    // add a logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingRestController.class);
    private final TourRatingService tourRatingService;
    // method to create a new tour rating
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new tour rating given a tour id and a ratingDto object")
    public RatingDto createTourRating(@PathVariable("tourId") Long tourId,
                                 @RequestBody @Validated RatingDto ratingDto) {
        LOGGER.info("POST /tours/{}/ratings/{}",tourId,ratingDto);
        return tourRatingService.createTourRating(tourId, ratingDto);
    }

    // method to get all tour ratings for a tour
    @GetMapping
    @Operation(summary = "Get all ratings of all Tours given tour id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "TourId not found")})
    public List<RatingDto> getAllTourRatingsForTour(@PathVariable("tourId") Long tourId) {
        LOGGER.info("GET /tour/{}/ratings", tourId);
        return tourRatingService.getAllTourRatingsForTour(tourId);
    }

    // method to get average tour rating for a tour
    @GetMapping("/average")
    @Operation(summary = "Get average tour ratings of a Tour given TourId")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "TourId not found")})
    public Double getAverageTourRatingForTour(@PathVariable("tourId") Long tourId) {
        LOGGER.info("GET /tour/{}/ratings/average", tourId);
        return tourRatingService.getAverageTourRatingForTour(tourId);
    }

    // method to update a tour rating
    // Return is not mandatory here, but for Testing purposes we add the return of RatingDto
    @PutMapping
    @Operation(summary = "Update a tour rating given a tour id and a ratingDto object")
    public RatingDto updateTourRating(@PathVariable("tourId") Long tourId,
                                 @RequestBody @Validated RatingDto ratingDto) {
        // add logger info along with url parameter
        LOGGER.info("PUT /tour/{}/ratings/{}",tourId, ratingDto);
        return tourRatingService.updateTourRating(tourId, ratingDto);
    }

    // method to delete a tour rating
    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete a tour rating given a tour id and a customerId")
    public void deleteTourRating(@PathVariable("tourId") Long tourId,
                                 @PathVariable("customerId") Integer customerId) {
        // add logger info along with url parameter
        LOGGER.info("DELETE /tour/{}/ratings/{}",tourId,customerId);
        tourRatingService.deleteTourRating(tourId, customerId);
    }

    // method to get tour rating score by customer
    @GetMapping("/{customerId}")
    @Operation(summary = "Get tour rating score by customer given a tour id and a customerId")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "TourId or CustomerId not found")})
    public Integer getTourRatingScoreByCustomer(@PathVariable("tourId") Long tourId,
                                             @PathVariable("customerId") Integer customerId) {
        // add logger info with url parameter
        LOGGER.info("GET /tour/{}/ratings/{}", tourId,customerId);
        return tourRatingService.getTourRatingScoreByCustomer(tourId, customerId);
    }

    // method to get rating dto by tour and customer
    @GetMapping("/ratingDto/{customerId}")
    @Operation(summary = "Get rating dto given a tour id and a customerId")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "TourId or CustomerId not found")})
    public RatingDto getRatingDtoByTourAndCustomer(@PathVariable("tourId") Long tourId,
                                                     @PathVariable("customerId") Integer customerId) {
        // add logger info with url parameter
        LOGGER.info("GET /tour/{}/ratings/{}", tourId,customerId);
        return tourRatingService.getRatingDtoByTourAndCustomer(tourId, customerId);
    }


    // method to create a tour rating for a tour from a list of customer ids with tourId, score as path variable and customerIds request parameter
    @PostMapping("/{score}/customerIds")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a tour rating given a tour id and a score and a list of customer ids")
    public void createTourRatingByManyCustomers(@PathVariable("tourId") Long tourId,
                                                 @PathVariable("score") Integer score,
                                                 @RequestParam("customerIds") @Validated List<Integer> customerIds) {
        // add logger info with url parameter
        LOGGER.info("POST /tour/{}/ratings/{}/customerIds/{}",tourId,score,customerIds);
        tourRatingService.createTourRatingByManyCustomers(tourId, score, customerIds);
    }



    // method for exception handling if NoSuchElementException is thrown by this controller
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNoSuchElementException(NoSuchElementException e) {
        LOGGER.error("ExploreCli>>>[BY_JA]:: NoSuchElementException: {}", e.getMessage());
    }

    // method for exception handling if IllegalArgumentException is thrown by this controller
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException(IllegalArgumentException e) {
        LOGGER.error("ExploreCli>>>[BY_JA]:: IllegalArgumentException: {}", e.getMessage());
    }

    // method for exception handling if IllegalStateException is thrown by this controller
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalStateException(IllegalStateException e) {
        LOGGER.error("ExploreCli>>>[BY_JA]:: IllegalStateException: {}", e.getMessage());
    }


}
