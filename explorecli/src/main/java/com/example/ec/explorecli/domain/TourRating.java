package com.example.ec.explorecli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourRating {

    @EmbeddedId
    private TourRatingPK pk;

    @Column(nullable = false)
    private Integer score;

    @Column(length = 255)
    private String comment;
}
