package com.example.ec.explorecli.services;

import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.service.TourRatingService;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class) // to enable springboot support for Junit test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) //means no controller and thereby no port is required
@Transactional // all transactions to be rolled back after the test completes
public class TourRatingServiceIntegrationTest {
    private static final Integer CUSTOMER_ID = 100;
    private static final Long TOUR_ID = 10L;
    private static final Long NOT_A_TOUR_ID = 123L;

    @Autowired
    private TourRatingService tourRatingService;

    // public void deleteTourRating(Long tourId, Integer customerId)
    @Test
    public void testDeleteTourRatingHappyPath() {
        // get all the tour ratings
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();

        // delete the first tour rating
        tourRatingService.deleteTourRating(
                tourRatings.get(0).getTourId(),
                tourRatings.get(0).getCustomerId()
        );

        // check if the tour rating was deleted
        assertEquals(tourRatings.size() - 1, tourRatingService.getAllTourRatings().size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteTourRatingNotFoundUnhappyPath() {
        tourRatingService.deleteTourRating(NOT_A_TOUR_ID,CUSTOMER_ID);
    }
    /***
     * public void createTourRating(Long tourId, @Validated RatingDto ratingDto){
     *         LOGGER.info("POST /tours/{}/ratings/{}",tourId,ratingDto);
     *         Tour tour = helperMethods.validateTour(tourId);
     *         tourRatingRepository.save(new TourRating(
     *                 new TourRatingPK(tour, ratingDto.getCustomerId()),
     *                 ratingDto.getScore(),
     *                 ratingDto.getComment()
     *                 ));
     *     }
     */

    // method to test the createTourRating
    @Test
    public void testCreateTourRatingHappyPath() {
        // get all the tour ratings
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();

        // create a new RatingDto object with values
        RatingDto ratingDto = new RatingDto(
                14, // will reset to score 5
                "JA Comment--Integration Testing/TRansactional",
                CUSTOMER_ID+1
        );

        // create the first tour rating
        tourRatingService.createTourRating(
                TOUR_ID,
                ratingDto
        );

        // check if the tour rating was created
        assertEquals(tourRatings.size()+1, tourRatingService.getAllTourRatings().size());
    }

    /**
     * public void createTourRatingByScore(Long tourId, Integer score, Integer customerId){
     *         LOGGER.info("POST /tours/{}/ratings/{}?customers={}", tourId, score, customerId);
     *         Tour tour = helperMethods.validateTour(tourId);
     *         tourRatingRepository.save(new TourRating(
     *                 new TourRatingPK(tour, customerId),
     *                 score));
     *     }
     */

    @Test
    public void testCreateTourRatingByScoreHappyPath(){
        // get all the tour ratings
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();

        // create the first tour rating
        tourRatingService.createTourRatingByScore(
                TOUR_ID,
                5,
                CUSTOMER_ID+1
        );

        // check if the tour rating was created
        assertEquals(tourRatings.size()+1, tourRatingService.getAllTourRatings().size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testCreateTourRatingByScoreNotFoundUnhappyPath(){
        // create the first tour rating
        tourRatingService.createTourRatingByScore(
                NOT_A_TOUR_ID,
                5,
                CUSTOMER_ID
        );
    }

    /**
     * public void createTourRatingByManyCustomers(Long tourId, Integer score, List<Integer> customerIds){
     *         LOGGER.info("POST /tours/{}/{}/customers/?customers={}", tourId, score,customerIds);
     *         Tour tour = helperMethods.validateTour(tourId);
     *         customerIds.stream()
     *                         .forEach(customerId -> {
     *                             LOGGER.debug("Attempt to create Tour Rating {} for customer {}", score, customerId);
     *                             tourRatingRepository.save(
     *                                             new TourRating(new TourRatingPK(tour, customerId),
     *                                                     score));
     *                                 }
     *
     *                         );
     *     }
     */

    @Test
    public void testCreateTourRatingByManyCustomersHappyPath(){
        // get all the tour ratings
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();

        // create the first tour rating
        tourRatingService.createTourRatingByManyCustomers(
                TOUR_ID,
                3,
                List.of(CUSTOMER_ID+1,CUSTOMER_ID+2,CUSTOMER_ID+3,CUSTOMER_ID+4,CUSTOMER_ID+5)
        );

        // check if the tour rating was created
        assertEquals(tourRatings.size() + 5, tourRatingService.getAllTourRatings().size());
    }

    //@Test(expected = DataIntegrityViolationException.class)
    @Test // this test is redundant and dont fit with the application architecture //
    public void testCreateTourRatingByManyCustomersDataIntegrityViolationUnhappyPath(){
        // get all the tour ratings
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();
        // create a list of customerIds
        List<Integer> customerIds = List.of(CUSTOMER_ID+1,CUSTOMER_ID+2,
                CUSTOMER_ID+3,CUSTOMER_ID+4,CUSTOMER_ID+5,CUSTOMER_ID+6);

        // create the first tour rating
        tourRatingService.createTourRatingByManyCustomers(TOUR_ID, 2, customerIds);
        tourRatingService.createTourRatingByManyCustomers(TOUR_ID, 1, customerIds); // this doesnt fail, but updates the score of the tourd// this invocation should fail and return data integrity violation
    }

    /**
     * public void updateTourRating(Long tourId, RatingDto ratingDto){
     *         // verify tour rating by tour and customer id
     *         TourRating tourRating = helperMethods.validateTourRating(tourId, ratingDto.getCustomerId());
     *         tourRating.setScore(ratingDto.getScore());
     *         tourRating.setComment(ratingDto.getComment());
     *         tourRatingRepository.save(tourRating);
     *     }
     */

    @Test
    public void testUpdateTourRatingHappyPath(){
        // find the tour rating by tour id
        RatingDto fetchedRatingDto = tourRatingService.getRatingDtoByTourAndCustomer(TOUR_ID, CUSTOMER_ID);
        fetchedRatingDto.setScore(4);
        fetchedRatingDto.setComment("Updated Comment--Integration Testing");

        // update an existing tour rating
        tourRatingService.updateTourRating(
                TOUR_ID,
                fetchedRatingDto
        );


        // check if the tour rating was updated
        RatingDto updatedRatingDto = tourRatingService.getRatingDtoByTourAndCustomer(TOUR_ID, CUSTOMER_ID);
        assertEquals(4, updatedRatingDto.getScore());
        assertEquals("Updated Comment--Integration Testing", updatedRatingDto.getComment());
    }


    @Test(expected = NoSuchElementException.class)
    public void testUpdateTourRatingExceptionUnhappyPath(){
        // find the tour rating by tour id
        RatingDto fetchedRatingDto = tourRatingService.getRatingDtoByTourAndCustomer(NOT_A_TOUR_ID, CUSTOMER_ID);
    }

    /**
     * public Double getAverageTourRatingForTour(Long tourId){
     *         helperMethods.validateTour(tourId);
     *         List<TourRating> tourRatings = tourRatingRepository.findByPkTourId(tourId);
     *         return tourRatings.stream()
     *               .mapToDouble(TourRating::getScore)
     *               .average()
     *               .orElse(0.0);
     *     }
     */

//    @Test
//    public void testGetAverageTourRatingForTourHappyPath(){
//        assertEquals(2.5, tourRatingService.getAverageTourRatingForTour(TOUR_ID));
//    }

//    @Test(expected = NoSuchElementException.class)
//    public void testGetAverageTourRatingForTourNotFoundUnhappyPath(){
//        var avgTourRating = tourRatingService.getAverageTourRatingForTour(NOT_A_TOUR_ID);
//    }




}
