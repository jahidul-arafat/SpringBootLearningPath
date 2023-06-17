package com.example.testDrivenDevelopementWithJUnit.repo;

import com.example.testDrivenDevelopementWithJUnit.domain.CustomerContact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerContactRepository
        extends CrudRepository<CustomerContact, Long> {

}
