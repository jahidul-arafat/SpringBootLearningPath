package com.example.ec.explorecli.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TourRatingUnitTest {
    // Create a tour package
    private TourPackage tourPackage = new TourPackage("USA", "International USA PHD Trip");

    // create two tour objects with two distinct titles
    private Tour tour = new Tour(tourPackage, "Sample trip",
            "This is a test tour", "This is a test tour", "This is a test tour",
            Difficulty.Easy, "10:00", 10.0, Region.Varies, "International, USA, PHD, Trip");

    @Test // assertions will automatically throw an exception if incorrect values are passed
    public void testConstructor1() /*throws Exception*/ {
        // Create a tour rating object
        TourRating tourRating = new TourRating(
                new TourRatingPK(tour, -1),
                5);

        // Check that the tour rating object has the correct values
        // negative customer ID will be replaced with default value 12345678
        assertNull(tourRating.getTourId());
        assertEquals(5, tourRating.getScore());
        assertEquals(12345678, tourRating.getCustomerId());
        assertEquals("Great", tourRating.getComment());
    }

    @Test
    public void testConstructor2(){
        // Create a tour rating object
        TourRating tourRating = new TourRating(
                new TourRatingPK(tour, -2),
                -5,
                "DUMMY COMMENT");

        // Check that the tour rating object has the correct values
        assertNull(tourRating.getTourId());
        assertEquals(0, tourRating.getScore()); // negative score will be replaced with default value 0
        assertEquals(12345678, tourRating.getCustomerId());
        assertEquals("DUMMY COMMENT", tourRating.getComment());
    }

    @Test
    public void testEqualsHashCodeVerify() {
        // Create a tour rating object
        TourRating tourRating1 = new TourRating(
                new TourRatingPK(tour, 2),
                0,
                "DUMMY COMMENT");
        // Create a tour rating object
        TourRating tourRating2 = new TourRating(
                new TourRatingPK(tour, 2),
                -5, // negative score will be replaced with default value 0
                "DUMMY COMMENT");
        assertEquals(tourRating1.hashCode(), tourRating2.hashCode());

    }

}
