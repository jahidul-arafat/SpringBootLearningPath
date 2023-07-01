package com.example.ec.explorecli.services;

import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.repo.TourRatingRepository;
import com.example.ec.explorecli.repo.TourRepository;
import com.example.ec.explorecli.service.TourRatingService;
import com.example.ec.explorecli.util.HelperMethods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class) // MockitoExtension.class is the specific extension provided by the Mockito framework. It integrates Mockito with JUnit 5 tests.
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) // means, its not a controller test and we don't need a URL/endpoint.
@RunWith(MockitoJUnitRunner.class)
public class TourRatingServiceUnitTest {

    private static final Integer CUSTOMER_ID = 100;
    private static final Long TOUR_ID = 10L;
    private static final Long NOT_A_TOUR_ID = 123L;

    @Mock
    private TourRepository tourRepositoryMock; // injectable to TourRatingService
    @Mock
    private TourRatingRepository tourRatingRepositoryMock; // injectable to TourRatingService
    @Mock
    private HelperMethods helperMethodsMock;

    /*
    public class TourRatingService {
    private final TourRepository tourRepository;
    private final TourRatingRepository tourRatingRepository;
    private final HelperMethods helperMethods;

     */
    @InjectMocks
    private TourRatingService tourRatingService; // mocks are injected into the TourRatingService // this interacts with Tour and TourRating

    @Mock
    private Tour tourMock; // not injectable to TourRatingService /
    @Mock
    private TourRating tourRatingMock; // not injectable to TourRatingService
    @Mock
    private RatingDto ratingDtoMock; // not injectable to TourRatingService

    @Before // to override the normal behavior of runtime // this will be invoked before each test method
    public void setUpReturnValuesForMockInvocations() {
        /*
        1. Return (Optional<Tour>) when search tourRepository by TOUR_ID
        2. Return TOUR_ID, when invoke the Tour object by getId() method
        3. Return (Optional<TourRating>) when search tourRatingRepository by tour_id and customer_id
        4. Return a list of TourRating objects when search tourRatingRepository by TOUR_ID
         */
        //when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock)); // return the mocked Tour object when search with  TOUR_ID
        //when(tourMock.getId()).thenReturn(TOUR_ID);
//        when(tourRatingRepositoryMock.findByPkTourIdAndPkCustomerID(TOUR_ID, CUSTOMER_ID))
//                .thenReturn(Optional.of(tourRatingMock));
        when(tourRatingRepositoryMock.findByPkTourId(TOUR_ID))
                .thenReturn(List.of(tourRatingMock));
        //when(helperMethodsMock.validateTourRating(TOUR_ID, ratingDtoMock.getCustomerId())).thenReturn(tourRatingMock);

        when(ratingDtoMock.getComment()).thenReturn("Updated Comment");
        when(ratingDtoMock.getScore()).thenReturn(2);
        when(ratingDtoMock.getCustomerId()).thenReturn(CUSTOMER_ID);
//        when(tourRatingMock.getTourId()).thenReturn(TOUR_ID);
//        when(tourRatingMock.getScore()).thenReturn(10);
//        when(tourRatingMock.getCustomerId()).thenReturn(CUSTOMER_ID);
//        when(tourRatingMock.getComment()).thenReturn("Comment");

    }

    // Test method to verify that TourRatingService return values
    @Test // it's a mock test of 1 Tour
    public void testGetAllTourRatingsTest() {
        when(tourRatingRepositoryMock.findAll()).thenReturn(List.of(tourRatingMock)); // expected
        List<TourRating> actual = tourRatingService.getAllTourRatings(); // actual
        assertEquals(1, actual.size());
        assertEquals(tourRatingMock, actual.get(0));
    }

    // Test method to verify that TourRatingService return values based on the given tour_id
    @Test // it's a mock test of 1 Tour
    public void testGetAllTourRatingsByTourIdTest() {
        when(tourRatingRepositoryMock.findByPkTourId(TOUR_ID)).thenReturn(List.of(tourRatingMock)); // expected
        List<TourRating> actual = tourRatingService.getAllTourRatingsForTourByTourId(TOUR_ID); // actual
        assertEquals(1, actual.size());
        assertEquals(tourRatingMock, actual.get(0));
    }

    // Test method to verify the average score of a tour
    @Test
    public void testGetAverageTourRatingForTour() {
        when(tourRatingMock.getScore()).thenReturn(10); // expected
        Double actual = tourRatingService.getAverageTourRatingForTour(TOUR_ID); // actual
        assertEquals(10, actual);
    }

    // Test method to create a new Tour Rating
    //  this test method verifies that the createTourRating() method of tourRatingService correctly
    //  saves the TourRating object with the expected attributes to the tourRatingRepositoryMock
    //  by capturing the object passed to the save() method and asserting its attribute values.
    @Test
    public void testCreateTourRating() {
        //prepare to capture a TourRating Object
        // The ArgumentCaptor is used in this test to capture the TourRating object that is passed
        // as an argument to the save() method of tourRatingRepositoryMock. It allows you to
        // inspect and verify the attributes of the captured object to ensure that the correct
        // TourRating object is being saved.
        ArgumentCaptor<TourRating> tourRatingCaptor = ArgumentCaptor.forClass(TourRating.class);

        //invoke createNew
        tourRatingService.createTourRating(TOUR_ID, ratingDtoMock);

        //verify tourRatingRepository.save invoked once and capture the TourRating Object
        verify(tourRatingRepositoryMock).save(tourRatingCaptor.capture());

        //verify the attributes of the Tour Rating Object
        assertEquals(tourRatingCaptor.getValue().getCustomerId(), CUSTOMER_ID);
        assertEquals(tourRatingCaptor.getValue().getScore(),2);
        assertEquals(tourRatingCaptor.getValue().getComment(),"Updated Comment");

    }

    // Test method to update an existing Tour Rating
    @Test
    public void testUpdateSingleTourRating() {
        when(helperMethodsMock.validateTourRating(TOUR_ID, ratingDtoMock.getCustomerId())).thenReturn(tourRatingMock); // setup // without this TourRating will face a null pointer exception// expected
        tourRatingService.updateTourRating(TOUR_ID, new RatingDto(1, "great",100));

        //verify tourRatingRepository.save invoked once and capture the TourRating Object
        verify(tourRatingRepositoryMock).save(any(TourRating.class));

        verify(tourRatingMock).setComment("great");
        verify(tourRatingMock).setScore(1);
    }

    // Test method to delete an existing Tour Rating
    @Test
    public void testDeleteTourRating() {
        when(helperMethodsMock.validateTourRating(TOUR_ID, ratingDtoMock.getCustomerId())).thenReturn(tourRatingMock);
        tourRatingService.deleteTourRating(TOUR_ID, CUSTOMER_ID);
        //verify tourRatingRepository.delete invoked once and capture the TourRating Object
        verify(tourRatingRepositoryMock).delete(any(TourRating.class));
    }

    /*
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
     */

    // Test method to create tour rating by a list of customers given the tour id and score
    @Test
    public void testCreateTourRatingsByListOfScoreTourCustomer() {
        tourRatingService.createTourRatingByManyCustomers(TOUR_ID, 10, List.of(CUSTOMER_ID, CUSTOMER_ID+1, CUSTOMER_ID+2));
        //verify tourRatingRepository.save invoked three times
        verify(tourRatingRepositoryMock, times(3)).save(any(TourRating.class));
    }

}
