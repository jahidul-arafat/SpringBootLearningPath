package com.example.testDrivenDevelopementWithJUnit.controller;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import com.example.testDrivenDevelopementWithJUnit.service.ContactsManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class) // annotation is used to enable Spring support for the JUnit test
@WebMvcTest(ContactsManagementController.class) // to inform our test environment that we will be unit testing in MVC controller
public class ContactManagementControllerUnitTest {
    @Autowired // allows performing HTTP requests and assertions on the controller endpoints.
    private MockMvc mockMvc;

    /*
    - @Mock is used in traditional unit tests to create a mock instance of a class.
    - In the case of integration testing or component testing,
      where you want to test the interaction between different components of your application,
      such as a controller and a service, you would typically use @MockBean.
     */
    @MockBean // This creates a mock instance of the service, allowing the test to define the behavior of its methods.
    private ContactsManagementService contactsManagementService;

    @InjectMocks // This injects the mocked dependencies, in this case, the ContactsManagementService, into the controller.
    private ContactsManagementController contactsManagementController;

//    @BeforeEach // method is used to set up the test case.
//    public void setUp() {
//        MockitoAnnotations.openMocks(this); // it uses MockitoAnnotations.openMocks(this) to initialize the mocks defined in the test class.
//    }

    @Test
    public void testAddContactHappyPath() throws Exception {
        // setup mock Contact returned the mock service component
        CustomerContact mockCustomerContact = new CustomerContact();
        mockCustomerContact.setFirstName("Fred");
        mockCustomerContact.setLastName("Flintstone");

        when(contactsManagementService
                .addCustomerContact(any(CustomerContact.class)))
                .thenReturn(mockCustomerContact);

        // create a new Contact that will be added into the MVC for mock testing
        CustomerContact newContact = new CustomerContact();
//        newContact.setFirstName("Fred");
//        newContact.setEmail("ychag@example.com");

        // perform the HTTP request to add a new Contact into the MVC as post method
        mockMvc
                .perform(post("/contacts/model/addContact",newContact))
                .andExpect(status().is(201)) // means post method is successful and newContact is created successfully
                .andExpect(jsonPath("$.firstName").value("Fred"))
                .andExpect(jsonPath("$.lastName").value("Flintstone"))
                .andReturn();
    }

    @Test
    public void testAddContactBizConstraintsNotSatisfied() throws Exception {
        // setup a mock response of NULL object returned from the mock service component
        when(contactsManagementService
                .addCustomerContact(any(CustomerContact.class)))
                .thenReturn(null); // return an empty string

        // simulate the form bean that would POST from the web page
        CustomerContact aContact = new CustomerContact();
        aContact.setLastName("Johnson");

        // simulate the form submit (POST)
        mockMvc
                .perform(post("/contacts/model/addContact", aContact))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
                .andReturn();
    }




}
