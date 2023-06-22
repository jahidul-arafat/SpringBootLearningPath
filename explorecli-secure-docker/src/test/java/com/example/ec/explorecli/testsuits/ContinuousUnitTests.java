package com.example.ec.explorecli.testsuits;

import com.example.ec.explorecli.domain.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegionUnitTest.class,
        RegionConverterUnitTest.class,
        TourPackageUnitTest.class,
        TourUnitTest.class,
        TourRatingUnitTest.class
})
public class ContinuousUnitTests {
    // no body required
    // if no runnable method triggered, means you might have imported Junit 5 '@Test' instead of Junit4 '@Test'
    // @RunWith requires JUnit4 '@Test' annotation
}
