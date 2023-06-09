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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



//@ExtendWith(SpringExtension.class) // annotation is used to enable Spring support for the JUnit test
//@WebMvcTest(RatingController.class) // to inform our test environment that we will be unit testing in MVC cont
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = RANDOM_PORT)
@WebMvcTest(RatingController.class)
//@AutoConfigureMockMvc // to disable Spring Security for your unit tests
public class RatingControllerTest {
    private static final String RATINGS_URL = "/rating";

    //These Tour and rating id's do not already exist in the db
    private static final Long TOUR_ID = 999L;

    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";

    @Autowired // allows performing HTTP requests and assertions on the controller endpoints.
    private MockMvc mockMvc;

    @MockBean
    private TourRatingService tourRatingServiceMock;    // its a spring context bean

    @MockBean
    private RatingController ratingControllerMock;  // its a spring context bean

    @MockBean // will initialize at @Before to avoid serialization error
    private TourRating tourRatingMock;  // its a spring context bean:: Entity

    @Mock
    private TourRatingPK tourRatingPKMock;  // its not a spring context bean

    @MockBean
    private Tour tourMock;      // a Spring context bean

    // @Mock // we removed this mock as its consistently raising serialization error // we will define it at @Before
    private RatingDto ratingDtoMock;    // not a spring context bean

    //@Mock // we removed this Mock tag as its consistently raising serialization error
    private CustomerTourRefDto customerTourRefDtoMock; // we have to define it at @Before   // not a spring context bean



    @Before
    public void setup() {
        // Set mock values as needed for tourRatingMock to avoid serialization error
//        tourRatingMock = new TourRating();
//        tourRatingPKMock = new TourRatingPK();
//        tourRatingPKMock.setTour(tourMock);
//        tourRatingPKMock.setCustomerID(CUSTOMER_ID);
//
//        tourRatingMock.setPk(tourRatingPKMock);
//        tourRatingMock.setScore(SCORE);
//        tourRatingMock.setComment(COMMENT);

        //TourRatingPK tourRatingPK = new TourRatingPK(tourMock, CUSTOMER_ID);
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


    // Test method to get all ratings of a tour
    @Test
    //@WithMockUser(username = "admin", password = "admin")
    public void testGetAllRatingsOfTourByTourId() throws Exception {
        when(ratingControllerMock.getAllRatings())
                .thenReturn(List.of(ratingDtoMock));
        //List<TourRating> actual =  tourRatingServiceMock.getAllTourRatingsForTourByTourId(TOUR_ID);
        List<RatingDto> actual = ratingControllerMock.getAllRatings();
        assertEquals(1, actual.size());
    }

    @Test
    //@WithMockUser(username = "admin", password = "admin")
    public void testGetAllRatingsByScore() throws Exception {
        when(ratingControllerMock.getAllRatingsByScore(SCORE)).thenReturn(List.of(
                customerTourRefDtoMock,customerTourRefDtoMock,customerTourRefDtoMock));
        List<CustomerTourRefDto> actual = ratingControllerMock.getAllRatingsByScore(SCORE);
        assertEquals(3, actual.size());
    }

    // Test method
    /**
     *  HTTP GET /rating/{score}
     *  /rating/4
     */
    @Test
    //@WithMockUser(username = "admin", password = "admin")
    public void testGetRatingByScoreWebURL() throws Exception {
        List<CustomerTourRefDto> mockList = List.of(
                customerTourRefDtoMock, customerTourRefDtoMock, customerTourRefDtoMock);

        when(ratingControllerMock.getAllRatingsByScore(SCORE)).thenReturn(mockList);

        mockMvc.perform(MockMvcRequestBuilders.get(RATINGS_URL + "/{score}", SCORE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].customerId").value(CUSTOMER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tourId").value(TOUR_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].comment").value(COMMENT));
    }

    /**
     *  HTTP GET /rating
     */
    @Test
    //@WithMockUser(username = "admin", password = "admin")
    public void testGetRatingWebURL() throws Exception {
        List<RatingDto> mockList = List.of(
                ratingDtoMock, ratingDtoMock, ratingDtoMock,ratingDtoMock);
        when(ratingControllerMock.getAllRatings()).thenReturn(mockList);
        mockMvc.perform(MockMvcRequestBuilders.get(RATINGS_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].customerId").value(CUSTOMER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].score").value(SCORE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].comment").value(COMMENT));
    }

    /*
    mockMvc
                .perform(post("/contacts/model/addContact",newContact))
                .andExpect(status().is(201)) // means post method is successful and newContact is created successfully
                .andExpect(jsonPath("$.firstName").value("Fred"))
                .andExpect(jsonPath("$.lastName").value("Flintstone"))
                .andReturn();
     */
    /**
     * HTTP POST /rating/{score}/{tourId}/{customerId}
     */
    @Test
    //@WithMockUser(username = "admin", password = "admin")
    public void testRateTourWebURL() throws Exception {
        when(ratingControllerMock.rateTour(SCORE, TOUR_ID, CUSTOMER_ID)).thenReturn(ratingDtoMock);
        mockMvc.perform(MockMvcRequestBuilders.post(RATINGS_URL + "/{score}/{tourId}/{customerId}", SCORE, TOUR_ID, CUSTOMER_ID))
              .andExpect(MockMvcResultMatchers.status().isCreated())
              .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(CUSTOMER_ID))
              .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(SCORE))
              .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(COMMENT));
    }



}
