package com.example.ec.explorecli.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(0)
    @Max(5)
    private Integer score;

    @Column(length = 255)
    private String comment;

    // constructor
    public TourRating(TourRatingPK pk, Integer score) {
        this.pk = pk;
        this.score = score;
        this.comment = toComment(score);
    }

    private String toComment(Integer score) {
        return switch (score) {
            case 0 -> "JA Said.... ZERO ....0000.......";
            case 1 -> "Terrible";
            case 2 -> "Poor";
            case 3 -> "Fair";
            case 4 -> "Good";
            case 5 -> "Great";
            default -> score.toString();
        };
    }

    // method to get tour_id and customerID
    public Integer getCustomerId() {
        return pk.getCustomerID();
    }

    public Long getTourId() {
        return pk.getTour().getId();
    }


}
