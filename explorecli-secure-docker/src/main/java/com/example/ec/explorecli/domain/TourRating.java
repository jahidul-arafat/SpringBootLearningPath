package com.example.ec.explorecli.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tour_id", "customerID"}))
public class TourRating {

    @EmbeddedId // This means that a single customer can rate a tour_id only once, as the combination of tour_id and customerID will be unique.
    private TourRatingPK pk;

    @Column(nullable = false)
    private Integer score;

    @Column(length = 255)
    private String comment;

    // constructor
    public TourRating(TourRatingPK pk, Integer score) {
        this.pk = pk;
        this.score = score;
        this.comment = "Null Comment by All customers";
    }

}
