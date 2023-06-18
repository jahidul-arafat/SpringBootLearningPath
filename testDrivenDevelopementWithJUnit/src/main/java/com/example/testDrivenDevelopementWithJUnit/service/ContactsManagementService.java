package com.example.testDrivenDevelopementWithJUnit.service;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import com.example.testDrivenDevelopementWithJUnit.repo.CustomerContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ContactsManagementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsManagementService.class);

    private CustomerContactRepository customerContactRepository;

    // constructor injection as a part of spring dependency injection
    public ContactsManagementService(CustomerContactRepository customerContactRepository) {
        this.customerContactRepository = customerContactRepository;
    }

    // method to add a new customer contact given a customer contact object
    public CustomerContact addCustomerContact(CustomerContact customerContact) {
        CustomerContact newContact = null;
//        return (customerContact.getFirstName() != null)?
//                customerContactRepository.save(customerContact) : newContact;
        if (customerContact.getFirstName() != null) {
            return customerContactRepository.save(customerContact);
        }
        return newContact;
    }

    public CustomerContact addCustomerContactWithServerErrors(CustomerContact customerContact) {
            return customerContactRepository.save(customerContact);
    }




    // method to get a customer contact given a customer contact id
    public CustomerContact getCustomerContactById(Long id) {
        return customerContactRepository.findById(id).orElse(null);
    }

    // method to get all customer contacts
    public Iterable<CustomerContact> getAllCustomerContacts() {
        return customerContactRepository.findAll();
    }

    // method to delete a customer contact given a customer contact id
    public void deleteCustomerContactById(Long id) {
        customerContactRepository.deleteById(id);
    }




}
