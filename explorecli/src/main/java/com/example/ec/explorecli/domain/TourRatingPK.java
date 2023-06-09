package com.example.ec.explorecli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourRatingPK implements Serializable {
    @ManyToOne
    private Tour tour;

//    @Column(name = "tour_id")
//    private Long tourId;

    @Column(nullable = false,updatable = false,insertable = false)
    private Integer customerID;
}
