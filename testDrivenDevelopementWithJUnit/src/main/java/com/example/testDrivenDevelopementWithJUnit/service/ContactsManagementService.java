package com.example.testDrivenDevelopementWithJUnit.service;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import com.example.testDrivenDevelopementWithJUnit.repo.CustomerContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactsManagementService {

    private CustomerContactRepository customerContactRepository;

    // constructor injection as a part of spring dependency injection
    public ContactsManagementService(CustomerContactRepository customerContactRepository) {
        this.customerContactRepository = customerContactRepository;
    }

    // method to add a new customer contact given a customer contact object
    public CustomerContact addCustomerContact(CustomerContact customerContact) {
        return customerContactRepository.save(customerContact);
    }
}
