package com.example.testDrivenDevelopementWithJUnit.service;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import lombok.NoArgsConstructor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class) // This annotation ensures that the necessary Spring context is loaded before executing the test.
@SpringBootTest(webEnvironment = WebEnvironment.NONE) // means we are not going to test any controller // The @SpringBootTest annotation is used to indicate that the test should load the entire Spring application context for testing purposes.
public class ContactManagementServiceIntegrationTest {
    // the test requires exactly 1 zero argument constructor
    /*
     @Autowired annotation, when used on a constructor parameter, signals to Spring that it should use
     constructor injection and handle the dependency resolution, even if a zero-argument
     constructor is not present.
     */
    //@Autowired // without this, the test will fail for a Zero-argument constructor requirement
    @Autowired
    private ContactsManagementService contactsManagementService;  // this is the service we are testing
    // constructor
    //@Autowired
//    public ContactManagementServiceIntegrationTest(ContactsManagementService contactsManagementService) {
//        this.contactsManagementService = contactsManagementService;
//    }

    @Test
    public void testSaveContactHappyPath() {
        // create a new customer contact
        CustomerContact contact = new CustomerContact();
        contact.setFirstName("Ailly");
        contact.setLastName("Aow");

        // save the contact
        CustomerContact newContact = contactsManagementService.addCustomerContact(contact);

        // verify the addition of the customer contact
        // smoke test 01: Check that the new contact is not null
        assertNotNull(newContact);

        // smoke test 02: Check that the new contact has an id
        assertNotNull(newContact.getId());

        // smoke test 03: Check that the new contact has the correct values
        assertEquals("Ailly", newContact.getFirstName());

    }
}