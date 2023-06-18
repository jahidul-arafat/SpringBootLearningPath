package com.example.testDrivenDevelopementWithJUnit.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_contact")
//@SequenceGenerator(name = "customer_contact_seq", sequenceName = "customer_contact_seq", allocationSize = 1)
public class CustomerContact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false) // it ensures that a non-null value is always required for the firstName field when saving or updating the corresponding entity in the database.
    private String firstName;

    private String lastName;
    private String email;
    private String deliveryAddressLine1;
    private String deliveryAddressLine2;
    private String deliveryAddressCity;
    private String deliveryAddressState;
    private String deliveryAddressLZipCode;

}
