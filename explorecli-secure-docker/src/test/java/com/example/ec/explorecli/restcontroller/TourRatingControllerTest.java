package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.domain.TourRatingPK;
import com.example.ec.explorecli.dto.CustomerTourRefDto;
import com.example.ec.explorecli.dto.RatingDto;
import com.example.ec.explorecli.service.TourRatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class) // This annotation is used to run the tests with the Spring test runner.
@WebMvcTest(TourRatingRestController.class) // This annotation tells Spring to load only the necessary web-related components for testing the
public class TourRatingControllerTest {
    private static final String TOUR_RATINGS_URL = "/tours";

    //These Tour and rating id's do not already exist in the db
    private static final Long TOUR_ID = 999L;

    private static final Long NOT_A_TOUR_ID=123123123L;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;

    private static final Double AVERAGE_RATING = 4.0;
    private static final String COMMENT = "comment";
    /*
    Why Autowired?
    Using @Autowired helps simplify the testing process and promotes code reusability
    by allowing you to inject dependencies rather than creating them manually.
     */

    @Autowired // which is a main entry point for testing Spring MVC applications. It allows performing HTTP requests and assertions on the controller endpoints.
    private MockMvc mockMvc;

    @Autowired // This annotation injects an instance of ObjectMapper, which is used for JSON serialization and deserialization in the tests.
    private ObjectMapper objectMapper;

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

    @Before // This annotation is used to indicate that the annotated method should be executed before each test method.
    public void setup() {
        // Set the default media type to application/json
//        mockMvc = MockMvcBuilders.standaloneSetup(tourRatingRestControllerMock)
//                .defaultRequest(MockMvcRequestBuilders.get("/")
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .build();

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

    // Test to check when the TOUR_ID does not exist in the db
    @Test
    public void testGetAllTourRatingsForTour_WhenTourIdNotFound() throws Exception {
        when(tourRatingRestControllerMock.getAllTourRatingsForTour(NOT_A_TOUR_ID))
              .thenReturn(Collections.emptyList());

        mockMvc
                .perform(MockMvcRequestBuilders.get(TOUR_RATINGS_URL + "/{tourId}/ratings", NOT_A_TOUR_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());

        // verify that the method invoked atleast once
        verify(tourRatingRestControllerMock).getAllTourRatingsForTour(NOT_A_TOUR_ID);

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
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(ratingDtoMock))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(CUSTOMER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(SCORE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(COMMENT));

        // verify that the TourRatingRestController getRatingDtoByTourAndCustomer() method is invoked once
        verify(tourRatingRestControllerMock).getRatingDtoByTourAndCustomer(TOUR_ID,CUSTOMER_ID);
    }

    /**
     * HTTP POST /tours/{tourId}/ratings
     */
    @Test
    public void testCreateTourRating() throws Exception {
        when(tourRatingRestControllerMock.createTourRating(TOUR_ID, ratingDtoMock))
                .thenReturn(ratingDtoMock);

        String requestBody = objectMapper.writeValueAsString(ratingDtoMock);

        // The request is configured with the content type MediaType.APPLICATION_JSON and the request body is set to
        // the serialized form of the ratingDtoMock object using the objectMapper.

        mockMvc.perform(MockMvcRequestBuilders.post(TOUR_RATINGS_URL + "/{tourId}/ratings", TOUR_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(ratingDtoMock))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(CUSTOMER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(SCORE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(COMMENT));


        // verify that the TourRatingRestController createTourRating() method is invoked once
        verify(tourRatingRestControllerMock).createTourRating(TOUR_ID, ratingDtoMock);
    }

    /**
     * HTTP PUT /tours/{tourId}/ratings/{ratingDto}
     */
    @Test
    public void testUpdateTourRating() throws Exception {
        when(tourRatingRestControllerMock.updateTourRating(TOUR_ID, ratingDtoMock))
               .thenReturn(ratingDtoMock);

        // The objectMapper is used to serialize the ratingDtoMock object into a JSON string,
        // which will be the request body.

        String requestBody = objectMapper.writeValueAsString(ratingDtoMock);

        // The request is configured with the content type MediaType.APPLICATION_JSON and the request body is set to
        // the serialized form of the ratingDtoMock object using the objectMapper.

        mockMvc.perform(MockMvcRequestBuilders.put(TOUR_RATINGS_URL + "/{tourId}/ratings", TOUR_ID)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(ratingDtoMock))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(CUSTOMER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(SCORE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(COMMENT));

        // verify that the TourRatingRestController updateTourRating() method is invoked once
        verify(tourRatingRestControllerMock).updateTourRating(TOUR_ID, ratingDtoMock);
    }


}
