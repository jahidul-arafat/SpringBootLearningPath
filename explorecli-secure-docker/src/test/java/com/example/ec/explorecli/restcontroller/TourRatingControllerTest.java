package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.domain.TourRatingPK;
import com.example.ec.explorecli.dto.CustomerTourRefDto;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.service.TourRatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(TourRatingRestController.class)
public class TourRatingControllerTest {
    private static final String TOUR_RATINGS_URL = "/tours";

    //These Tour and rating id's do not already exist in the db
    private static final Long TOUR_ID = 999L;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;

    private static final Double AVERAGE_RATING = 4.0;
    private static final String COMMENT = "comment";

    @Autowired // allows performing HTTP requests and assertions on the controller endpoints.
    private MockMvc mockMvc;

    @MockBean
    private TourRatingService tourRatingServiceMock;

    @MockBean
    private TourRatingRestController tourRatingRestControllerMock;

    @Mock // will initialize at @Before to avoid serialization error
    private TourRating tourRatingMock;

    @Mock
    private TourRatingPK tourRatingPKMock;

    @Mock
    private Tour tourMock;

    // @Mock // we removed this mock as its consistently raising serialization error // we will define it at @Before
    private RatingDto ratingDtoMock;

    //@Mock // we removed this Mock tag as its consistently raising serialization error
    private CustomerTourRefDto customerTourRefDtoMock; // we have to define it at @Before

    @Before
    public void setup() {
        // TourRatingPK tourRatingPK = new TourRatingPK(tourMock, CUSTOMER_ID);
        when(tourRatingMock.getPk()).thenReturn(tourRatingPKMock);
        when(tourMock.getId()).thenReturn(TOUR_ID);
        when(tourRatingMock.getComment()).thenReturn(COMMENT);
        when(tourRatingMock.getScore()).thenReturn(SCORE);
        when(tourRatingMock.getCustomerId()).thenReturn(CUSTOMER_ID);

        // Set mock values as needed for customerTourRefDtoMock to avoid serialization error
        customerTourRefDtoMock = new CustomerTourRefDto();
        customerTourRefDtoMock.setComment(COMMENT);
        customerTourRefDtoMock.setCustomerId(CUSTOMER_ID);
        customerTourRefDtoMock.setTourId(TOUR_ID);

        // Set mock values as needed for ratingDtoMock to avoid serialization error
        ratingDtoMock = new RatingDto();
        ratingDtoMock.setComment(COMMENT);
        ratingDtoMock.setCustomerId(CUSTOMER_ID);
        ratingDtoMock.setScore(SCORE);
    }

    /**
     * HTTP GET /tours/{tourId}/ratings
     */

    /*
     mockMvc.perform(MockMvcRequestBuilders.get(RATINGS_URL + "/{score}", SCORE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].customerId").value(CUSTOMER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tourId").value(TOUR_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].comment").value(COMMENT));

     */
    @Test
    public void testGetAllTourRatingsForTourByTourId() throws Exception {
        when(tourRatingRestControllerMock.getAllTourRatingsForTour(TOUR_ID))
                .thenReturn(List.of(ratingDtoMock,ratingDtoMock,ratingDtoMock));

        mockMvc
                .perform(MockMvcRequestBuilders.get(TOUR_RATINGS_URL + "/{tourId}/ratings", TOUR_ID))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                  .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(CUSTOMER_ID))
                  .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value(COMMENT))
                  .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(SCORE));
    }

    /**
     * HTTP GET /tours/{tourId}/ratings/average
     */

    // Test method to get average rating for a tour given tour id
    @Test
    public void testGetAverageRatingForTourByTourId() throws Exception {
        when(tourRatingRestControllerMock.getAverageTourRatingForTour(TOUR_ID))
              .thenReturn(AVERAGE_RATING);

        mockMvc
              .perform(MockMvcRequestBuilders.get(TOUR_RATINGS_URL + "/{tourId}/ratings/average", TOUR_ID))
                 .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                 .andExpect(MockMvcResultMatchers.jsonPath("$").value(AVERAGE_RATING));
    }

    /**
     * HTTP DELETE /tours/{tourId}/ratings/{customerId}
     */
    @Test
    public void testDeleteTourRatingByTourIdAndCustomerId() throws Exception {
        mockMvc
              .perform(MockMvcRequestBuilders.delete(TOUR_RATINGS_URL + "/{tourId}/ratings/{customerId}", TOUR_ID, CUSTOMER_ID))
              .andExpect(MockMvcResultMatchers.status().isOk());

        // The verify method is used to ensure that a specific method, deleteTourRating, is called on the
        // tourRatingRestControllerMock object with the provided TOUR_ID and CUSTOMER_ID arguments.
        // Error: Wanted but not invoked
        verify(tourRatingRestControllerMock).deleteTourRating(TOUR_ID, CUSTOMER_ID);
    }

    /**
     * HTTP GET /tours/{tourId}/ratings/ratingDto/{customerId}
     */
    @Test
    public void testGetTourRatingByTourIdAndCustomerId() throws Exception {
        when(tourRatingRestControllerMock.getRatingDtoByTourAndCustomer(TOUR_ID, CUSTOMER_ID))
             .thenReturn(ratingDtoMock);

        mockMvc
             .perform(MockMvcRequestBuilders.get(TOUR_RATINGS_URL + "/{tourId}/ratings/ratingDto/{customerId}", TOUR_ID, CUSTOMER_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(ratingDtoMock));
    }


}
