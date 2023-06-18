package com.example.testDrivenDevelopementWithJUnit.controller;


import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // means, we want to simulate a real web environment with Servlest enginer running at a random port
public class ContactsManagementControllerIntegrationTest {
    // lets inject the actual controller class we want to test
    @Autowired
    private ContactsManagementController contactsManagementController;

    @Test // the CustomerContact is not null and added to the Database
    public void testAddContactHappyPath() {
        CustomerContact aContact = new CustomerContact();
        aContact.setFirstName("LOTTO");
        aContact.setLastName("TOTTO");

        // post the CustomerContact to the controller
        CustomerContact returnedCustomerContact = contactsManagementController
                .processAddContact(aContact);

        // Assert that the outcome is equal to "success"
        assertEquals("LOTTO", returnedCustomerContact.getFirstName());

    }

    @Test   // the CustomerContact is null and thus the controller will return a failure
    public void testAddContactFail() {
        CustomerContact aContact = new CustomerContact(); // we didn't add the customer details
        aContact.setLastName("LastName");

        // post the CustomerContact to the controller
        // hence, no first name is set in the CustomerContact, it will be null
        CustomerContact returnedCustomerContact = contactsManagementController
                .processAddContact(aContact);

        // Assert that the outcome is equal to "failure"
        //assertEquals("failure", outcome.toLowerCase());
        assertNull(returnedCustomerContact);
    }

}
