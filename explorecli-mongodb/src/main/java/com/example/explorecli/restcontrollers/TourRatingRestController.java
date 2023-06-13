package com.example.explorecli.restcontrollers;

import com.example.explorecli.domain.TourRating;
import com.example.explorecli.services.TourRatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/tours/{tourId}/ratings") // this Controller validate the RequestBody which having the RatingDto as the bag of data
@AllArgsConstructor
public class TourRatingRestController {
    private final TourRatingService tourRatingService;

    // RESTful API-1: Create a Tour Rating
    // call the createTourRating() for class <TourRatingService>
    // Payload ratingDTO must be validated // Java Validation
    // NoSuchElementException
    @PostMapping // /api/{tourId}/ratings
    @ResponseStatus(HttpStatus.CREATED)// Http 200
    public void createTourRating(@PathVariable("tourId") String tourId, @RequestBody @Validated TourRating tourRating){
        tourRatingService.createTourRating(tourId, tourRating);
    }


    // RESTful API-2: Get all the rating of a tour
    @GetMapping // /tours/{tourId}/ratings
    public List<TourRating> getAllRatingsForTour(@PathVariable("tourId") String tourId){
        return tourRatingService.getAllRatingsForTour(tourId);
    }

    // http://localhost:8080/tours/1/ratings/pages?size=2&page=1&sort=score,desc
    /*
    *
    * page=1; means second page; first page is page 0
    * size=2; means number of elements per page
    * sort=score,desc; means sort by value <score> in descending order
     */
    @GetMapping("/pages") // /tours/{tourId}/ratings/pages
    public Page<TourRating> getAllRatingsForTour(@PathVariable("tourId") String tourId, Pageable pageable){
        return tourRatingService.getAllRatingsForTour(tourId, pageable);
    }


    // RESTful API-3: Get the average rating of a tour
    // NoSuchElementException
    @GetMapping("/average") // /tours/{tourId}/ratings/average
    public Map<String,Double> getAverageRatingOfTour(@PathVariable("tourId") String tourId){
        return tourRatingService.getAverageRatingOfTour(tourId);

    }

    // RESTful API-4: Update the RatingDTO Score and Comment made by a customerId with tourId
    // For URL>> PUT /tours/{tourId}/ratings
    // Update attribute even if no changes found
    @PutMapping // /api/{tourId}/ratings
    public TourRating updateWithPut(@PathVariable("tourId") String tourId, @RequestBody @Validated TourRating tourRating){
        return tourRatingService.updateWithPut(tourId, tourRating);
    }

    // RESTful API-5: Update the RatingDTO Score and Comment made by a customer with tourId
    // For URL>> PATCH /tours/{tourId}/ratings
    // Only update attribute if any changes found, else not
    @PatchMapping // /api/{tourId}/ratings
    public TourRating updateWithPatch(@PathVariable("tourId") String tourId, @RequestBody @Validated TourRating tourRating){
        return tourRatingService.updateWithPatch(tourId, tourRating);
    }

    // RESTful API-6: Delete a Rating of a tour (toudId Pk) made by a customer (customerId Pk)
    // For URL>> DELETE /tours/{tourId}/ratings/{customerId}
    @DeleteMapping("/{customerId}")
    public void deleteRating(@PathVariable("tourId") String tourId, @PathVariable("customerId") String customerId){
        tourRatingService.deleteRating(tourId,customerId);
    }

    // RESTful API-7: Get the Rating made by a customer (customerId) given (tourId)
    // For URL>> GET /tours/{tourId}/ratings/{customerId}
    @GetMapping("{customerId}")
    public String getRatingByCustomer(@PathVariable("tourId") String tourId, @PathVariable("customerId") String customerId){
        return tourRatingService.getRatingByCustomer(tourId,customerId);

    }


    // Helper Method
    // Action from API-1, if failed // Http 400
    // what if this controller return a NoSuchElementFound exception on tourId, when verifying whether the tourId and the tour really exists
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){
        return ex.getMessage();
    }




}
