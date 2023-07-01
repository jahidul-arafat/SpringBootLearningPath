package com.example.ec.explorecli.dto;

import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.domain.TourRatingPK;
import com.example.ec.explorecli.repo.TourRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingAssemblerUnitTest {
    @Mock
    private TourRepository tourRepositoryMock;

    @Mock
    private ServletUriComponentsBuilder builderMock;

    @InjectMocks
    private RatingAssembler ratingAssembler;


    @Test // error: java.lang.IllegalStateException: No current ServletRequestAttributes // Need to resolve
    public void testToModel() {
        // Create a mock TourRating object
        TourRating tourRating = new TourRating(
                new TourRatingPK(null, 1),
                5,
                "Excellent");

        // Mock the behavior of ServletUriComponentsBuilder
        //ServletUriComponentsBuilder builderMock = mock(ServletUriComponentsBuilder.class);
        when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(builderMock);
        when(builderMock.build().toUriString()).thenReturn("http://localhost:8080");

        // Set the mock builder to the RatingAssembler
        ratingAssembler.setBuilder(builderMock);

        // Call the toModel method
        RatingDto ratingDto = ratingAssembler.toModel(tourRating);

        // Verify the self link
        Optional<Link> selfLink = ratingDto.getLink("self");
        assertEquals("http://localhost:8080", selfLink.map(Link::getHref).orElse(""));

        // Verify the tour link
        Optional<Link> tourLink = ratingDto.getLink("tour");
        assertEquals("http://localhost:8080/tours/1/ratings/1", tourLink.map(Link::getHref).orElse(""));
    }


//    @Test
//    public void testToCustomerTourRefDto() {
//        // Mock the ServletUriComponentsBuilder and provide fixed URI values
//        when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(builderMock);
//        when(builderMock.build().toUriString()).thenReturn("http://example.com");
//
//        TourRatingPK tourRatingPK = new TourRatingPK(new Tour(), 2);
//        TourRating tourRating = new TourRating(tourRatingPK, 4, "Good");
//        CustomerTourRefDto customerTourRefDto = ratingAssembler.toCustomerTourRefDto(tourRating);
//
//        assertEquals(2, customerTourRefDto.getCustomerId());
//        assertEquals(0L, customerTourRefDto.getTourId());
//        assertEquals("Good", customerTourRefDto.getComment());
//
//        // Assert the tour link
//        Optional<Link> tourLink = customerTourRefDto.getLink("tour");
//        assertEquals("http://example.com", tourLink.get().getHref());
//
//        // Assert other assertions as needed
//    }
}
