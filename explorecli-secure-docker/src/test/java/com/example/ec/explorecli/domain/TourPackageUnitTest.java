package com.example.ec.explorecli.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TourPackageUnitTest {
    @Test
    public void testConstructorAndGetters() {
        // create a tour package // code, name
        TourPackage tourPackage = new TourPackage("CC", "cc_name");

        // check the tour package // expected, actual
        assertNotNull(tourPackage);
        assertEquals("CC", tourPackage.getCode());
        assertEquals("cc_name", tourPackage.getName());
    }

    @Test
    public void testEqualsHashCodeVerify() {
        // create 2 tour packages // code, name
        TourPackage tourPackage1 = new TourPackage("CC", "cc_name");
        TourPackage tourPackage2 = new TourPackage("CC", "cc_name");

        // check the tour package // expected, actual
        assertEquals(tourPackage1, tourPackage2);
        assertEquals(tourPackage1.hashCode(), tourPackage2.hashCode());
    }
}
