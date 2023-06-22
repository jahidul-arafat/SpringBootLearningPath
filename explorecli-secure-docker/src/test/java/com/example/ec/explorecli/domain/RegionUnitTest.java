package com.example.ec.explorecli.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In JUnit 4, the `@RunWith` annotation is used to specify a test runner class that will be used to run the tests.
 * In the case of `@RunWith(SpringRunner.class)`, it indicates that the SpringRunner class should be used as the
 * test runner.
 *
 * When writing JUnit tests for Spring applications, `@RunWith(SpringRunner.class)` is used to integrate the
 * Spring TestContext Framework with JUnit. It allows the tests to benefit from Spring's features and functionalities,
 * such as dependency injection, transaction management, and application context configuration.
 *
 * By using `@RunWith(SpringRunner.class)`, the test class can leverage the capabilities provided by Spring,
 * such as loading the application context, performing dependency injection with `@Autowired`,
 * and accessing other Spring-specific features. This integration helps in writing comprehensive
 * and effective unit tests for Spring-based components, ensuring that the tests have access to the
 * necessary dependencies and can simulate the behavior of the application in a controlled environment.
 *
 * It's important to note that in JUnit 5 (JUnit Jupiter), the equivalent annotation for `@RunWith` is `@ExtendWith`.
 */
// @RunWith(SpringRunner.class) // JUnit4 // its not required here as we dont require any dependency injection here and no @Autowired annotation being used for any class object in the code
public class RegionUnitTest {
    @Test
    public void findRegionObjByStringName() /*throws Exception*/ { // If you don't explicitly throw an exception in this test method, it will still work fine because the assertions themselves can throw exceptions.
        assertEquals(Region.Central_Coast, Region.findByName("Central Coast")); // assertions itself can throw exceptions, if 'throws Exception' is not explicitly specified
        assertEquals(Region.Northern_California, Region.findByName("Northern California"));
        assertEquals(Region.Southern_California, Region.findByName("Southern California"));
        assertEquals(Region.Varies, Region.findByName("Varies"));
        // assertEquals(Region.Varies, Region.findByName("Unknown")); // to fail
    }

    @Test
    public void findRegionStringNameByStringName() /* throws Exception*/ {
        assertEquals("Central Coast",Region.Central_Coast.getName());
        assertEquals("Northern California", Region.Northern_California.getName());
        assertEquals("Southern California", Region.Southern_California.getName());
        assertEquals("Varies", Region.Varies.getName());
    }

}
