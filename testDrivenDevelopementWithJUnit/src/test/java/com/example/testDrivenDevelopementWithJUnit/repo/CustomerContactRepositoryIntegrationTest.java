package com.example.testDrivenDevelopementWithJUnit.repo;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * For this testing, we will not load any Controller, no Services
 * Only Repositories will be tested
 */

/**
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE):
 * This annotation is used to control the configuration of the test database. By default,
 * when using @DataJpaTest, Spring Boot replaces the application's configured database with an in-memory database for testing purposes. However, by specifying replace = AutoConfigureTestDatabase.Replace.NONE, you are indicating that the test should use the same configured database as the application.
 * This is useful when you want to test against a real database rather than an in-memory database.
 */
//@ExtendWith(SpringExtension.class) // // annotation is used to enable Spring support for the JUnit test
@RunWith(SpringRunner.class)
@DataJpaTest // gives us a lot of things we need for testing JPA code |
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // What type of test database we gonna use: Embedded DB / External DB/ Default DB configured in our class path // by default it will create an in-memory database for testing purposes; here we instructed it to test against an in-memory database the real database
public class CustomerContactRepositoryIntegrationTest {
    /**
     * By using @Autowired to inject the TestEntityManager, you can access its methods and
     * use them in your test methods to interact with the database,
     * perform assertions, and validate the behavior of your JPA code.
     */
    @Autowired //The @Autowired annotation is used in Spring to automatically inject dependencies into a class. In this case, it is used to inject an instance of the TestEntityManager into the test class.
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerContactRepository customerContactRepository; // this is the component we want to test.

    @Test
    public void testFindByEmail() {
        // create a customer contact
        CustomerContact customerContact = new CustomerContact();
        customerContact.setFirstName("JAJ");
        customerContact.setLastName("VIRAL");
        customerContact.setEmail("jahidapon@gmail.com");

        // persist the customer contact
        /**
         * When you call testEntityManager.persist(customerContact), the entity is stored in the in-memory database managed
         * by the TestEntityManager, but it is not persisted to the real database.
         * This ensures that the test remains isolated and does not affect the state of the actual database.
         */
        testEntityManager.persist(customerContact); // the entity manager will automatically persist/save the entity into the database.


        // fetch the customer contact by email
        CustomerContact fetchedCustomerObject = customerContactRepository.findByEmail("jahidapon@gmail.com");
        assertNotNull(fetchedCustomerObject);
        assertEquals("jahidapon@gmail.com", fetchedCustomerObject.getEmail());
    }
}
