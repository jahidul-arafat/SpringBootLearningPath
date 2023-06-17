package com.example.testDrivenDevelopementWithJUnit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery_address")
public class DeliveryAddress {
    @Id
    private Long id;

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zipCode;

}
