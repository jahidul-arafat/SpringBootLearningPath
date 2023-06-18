package com.example.testDrivenDevelopementWithJUnit.controller;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import com.example.testDrivenDevelopementWithJUnit.service.ContactsManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactsManagementController {
    private final ContactsManagementService contactsManagementService;

    // constructor injection
    public ContactsManagementController(ContactsManagementService contactsManagementService) {
        this.contactsManagementService = contactsManagementService;
    }

    @PostMapping("/body/addContact")
    //@ResponseStatus(HttpStatus.CREATED)
    public CustomerContact processAddContact(@RequestBody CustomerContact customerContact) {
        return contactsManagementService.addCustomerContact(customerContact);
        // return failure if newContact is null or empty else return success
        //return (newContact == null)? "failure" : "success";
    }

    @PostMapping("/withServerError/body/addContact")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerContact processAddContactWithServerError(@RequestBody CustomerContact customerContact) {
        return contactsManagementService.addCustomerContact(customerContact);
    }

    @PostMapping("/model/addContact")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerContact processAddContactModelAttribute(@ModelAttribute CustomerContact customerContact) {
        return contactsManagementService.addCustomerContact(customerContact);
        // return failure if newContact is null or empty else return success
        //return (newContact == null)? "failure" : "success";
    }

    // method to get the contact by id
    @GetMapping("/getContactById/{id}")
    public CustomerContact getContactById(@PathVariable Long id) {
        return contactsManagementService.getCustomerContactById(id);
    }

    // method to get all the contacts
    @GetMapping("/getAllContacts")
    public Iterable<CustomerContact> getAllContacts() {
        return contactsManagementService.getAllCustomerContacts();
    }

    // method to delete the contact by id
    @DeleteMapping("/deleteContactById/{id}")
    public void deleteContactById(@PathVariable Long id) {
        contactsManagementService.deleteCustomerContactById(id);
    }

}
