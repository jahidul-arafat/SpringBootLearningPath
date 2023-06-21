package com.example.testDrivenDevelopementWithJUnit.testsuites;

import com.example.testDrivenDevelopementWithJUnit.service.ContactManagementServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class) // Junit 4, not JUnit 5
@Suite.SuiteClasses({
        DatastoreSystemHealthTest.class, // new feature
        ContactManagementServiceIntegrationTest.class // earlier feature integration tests
})
public class ContinuousIntegrationTestSuite {
    // no code required here as @Suite will do the job for us

}
