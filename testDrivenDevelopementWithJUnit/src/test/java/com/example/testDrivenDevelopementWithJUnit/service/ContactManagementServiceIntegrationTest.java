package com.example.testDrivenDevelopementWithJUnit.service;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import lombok.NoArgsConstructor;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


//@ExtendWith(SpringExtension.class) // This annotation ensures that the necessary Spring context is loaded before executing the test.
@RunWith(SpringRunner.class)
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
        contact.setFirstName("Cilly");
        contact.setLastName("Cow");

        // save the contact
        CustomerContact newContact = contactsManagementService.addCustomerContact(contact);

        // verify the addition of the customer contact
        // smoke test 01: Check that the new contact is not null
        assertNotNull(newContact);

        // smoke test 02: Check that the new contact has an id
        assertNotNull(newContact.getId());

        // smoke test 03: Check that the new contact has the correct values
        assertEquals("Cilly", newContact.getFirstName());

    }

    @Test
    public void testSaveContactBizCaseConstraintMandatoryAttributeMissing() {
        // create a new customer contact
        CustomerContact contact = new CustomerContact();
        contact.setLastName("Cow"); // firstName is mandatory and missing here

        // save the contact
        CustomerContact newContact = contactsManagementService.addCustomerContact(contact);

        // verify the addition of the customer contact
        // smoke test 01: Check that the new contact is not null
        assertNull(newContact);
    }
}
