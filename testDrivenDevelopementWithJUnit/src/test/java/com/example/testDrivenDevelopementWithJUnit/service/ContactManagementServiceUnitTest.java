package com.example.testDrivenDevelopementWithJUnit.service;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import com.example.testDrivenDevelopementWithJUnit.repo.CustomerContactRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ContactManagementServiceUnitTest {
    @Mock
    private CustomerContactRepository customerContactRepository;

    @InjectMocks
    private ContactsManagementService contactManagementService;

//    @BeforeEach
//    public void setUp() {
//        // By using @ExtendWith(MockitoExtension.class), the mocks will be automatically initialized for each test method,
//        // eliminating the need for the deprecated initMocks method.
//
//    }

    @Test
    public void testAddContactHappyPath() {
        CustomerContact aMockContact = new CustomerContact();
        aMockContact.setFirstName("Cilly");
        aMockContact.setLastName("Cow");
        aMockContact.setEmail("cilly.cow@example.com");

        when(customerContactRepository.save(any(CustomerContact.class))).thenReturn(aMockContact);


        // save the contact
        CustomerContact newContact = contactManagementService.addCustomerContact(new CustomerContact());

        // verify the save
        assertNotNull(newContact);
        assertNull(newContact.getId()); // this gonna be null, as data is not from the repository
        assertEquals("Cilly", newContact.getFirstName());


    }


}
