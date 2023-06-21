package com.example.testDrivenDevelopementWithJUnit.testsuites;


import com.example.testDrivenDevelopementWithJUnit.controller.ContactsManagementControllerIntegrationTest;
import com.example.testDrivenDevelopementWithJUnit.repo.CustomerContactRepositoryIntegrationTest;
import com.example.testDrivenDevelopementWithJUnit.service.ContactManagementServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class) // for Junit 4, not Junit 5
@Suite.SuiteClasses({
        ContactsManagementControllerIntegrationTest.class,
        ContactManagementServiceIntegrationTest.class,
        CustomerContactRepositoryIntegrationTest.class
})
public final class AddContactFeatureTestSuite {
    // intentionally left empty - Test Suite annotations is sufficient
}
