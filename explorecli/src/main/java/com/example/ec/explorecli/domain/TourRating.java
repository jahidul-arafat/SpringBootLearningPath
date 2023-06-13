package com.example.ec.explorecli.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
