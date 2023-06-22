package com.example.ec.explorecli.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class TourRatingPK implements Serializable {
    @ManyToOne
    private Tour tour;

//    @Column(name = "tour_id")
//    private Long tourId;

    @Column(nullable = false, updatable = false, insertable = false)
    @PositiveOrZero(message = "Customer ID must be non-negative") // this doesn't work
    private Integer customerID;

    // constructor
    public TourRatingPK(Tour tour, Integer customerID) {
        this.tour = tour;
        // customerID must be non-negative and default value is 12345678
        this.customerID = customerID < 0 ? 12345678 : customerID;
    }
}
