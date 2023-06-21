package com.example.testDrivenDevelopementWithJUnit.repo;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @TestExecutionListeners: This annotation configures the test execution listeners for the test class.
 * Test execution listeners handle various events during the test execution.
 * In this case, it includes listeners for dependency injection, cleaning the context before test modes,
 * transaction management, and database unit testing.
 *
 * @DatabaseSetup(value = "classpath:test-datasets.xml"):
 * This annotation is provided by the DbUnit library.
 * It specifies the XML file containing the dataset that should be loaded before running the test.
 * The dataset typically includes test data that will be used for the test.
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextBeforeModesTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup(value = "classpath:test-datasets.xml")
public class CustomerContactRepositoryDbUnitTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerContactRepository customerContactRepository;

    // test method to find a customer contact by email address and do the assert testing
    @Test
    public void findCustomerContactByEmailAddress() {
        CustomerContact customerContact = customerContactRepository.findByEmail("jenny@myemail.com");
        assertNotNull(customerContact);
        assertEquals("jenny@myemail.com", customerContact.getEmail());
    }

    // test method to find a customer contact by id and do the assert testing
    @Test
    public void findCustomerContactById() {
        // TestEntityManager returns an instance of the entity class or null if the entity is not found
        CustomerContact foundCustomerContact = testEntityManager.find(CustomerContact.class, 1L);
        assertNotNull(foundCustomerContact);
        assertEquals("jenny@myemail.com", foundCustomerContact.getEmail());
    }


}
