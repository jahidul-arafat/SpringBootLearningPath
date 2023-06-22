package com.example.ec.explorecli.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TourUnitTest {
    @Test
    public void testTourConstructorAndGetters() {
        /*
        * public Tour(TourPackage tourPackage, String title,
                String blurb, String description, String bullets,
                Difficulty difficulty, String duration, Double price, Region region, String keywords)
        *
        * */

        TourPackage tourPackage = new TourPackage("USA", "International USA PHD Trip");
        // create a new tour object with the package, title, blurb, description, bullets, difficulty, duration, price,
        // region, and keywords
        Tour tour = new Tour(tourPackage, "International USA PHD Trip",
                "This is a test tour", "This is a test tour", "This is a test tour",
                Difficulty.Easy, "10:00", 10.0, Region.Varies, "International, USA, PHD, Trip");

        // check the package
        assertEquals(tourPackage, tour.getTourPackage());

        // check the title
        assertEquals("International USA PHD Trip", tour.getTitle());

        // check the blurb
        assertEquals("This is a test tour", tour.getBlurb());

        // check the description
        assertEquals("This is a test tour", tour.getDescription());

        // check the bullets
        assertEquals("This is a test tour", tour.getBullets());

        // check the difficulty
        assertEquals(Difficulty.Easy, tour.getDifficulty());

        // check the duration
        assertEquals("10:00", tour.getDuration());

        // check the price
        assertEquals(10.0, tour.getPrice());

        // check the region
        assertEquals(Region.Varies, tour.getRegion());

        // check the keywords
        assertEquals("International, USA, PHD, Trip", tour.getKeywords());

        // check the region
        assertEquals(Region.Varies, tour.getRegion());
    }

    @Test
    public void testEqualsHashCodeVerify() {
        // Create a tour package
        TourPackage tourPackage = new TourPackage("USA", "International USA PHD Trip");

        // create two tour objects with two distinct titles
        Tour tour1 = new Tour(tourPackage, "Sample trip",
                "This is a test tour", "This is a test tour", "This is a test tour",
                Difficulty.Easy, "10:00", 10.0, Region.Varies, "International, USA, PHD, Trip");
        Tour tour2 = new Tour(tourPackage, "Sample trip",
                "This is a test tour", "This is a test tour", "This is a test tour",
                Difficulty.Easy, "10:00", 10.0, Region.Varies, "International, USA, PHD, Trip");

        // check that the two tour objects are equal
        assertEquals(tour1, tour2);

        // check that the two tour objects have the same hash code
        assertEquals(tour1.hashCode(), tour2.hashCode());
    }
}
