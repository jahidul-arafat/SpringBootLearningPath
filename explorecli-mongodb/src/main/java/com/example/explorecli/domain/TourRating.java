package com.example.explorecli.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/*
* Customer (having a Unique Customer Id) can Rate A tour they have visited in 1 to 5 star
* and also can make comments on that
*
* Note
* ----
* TourRating Class with store the toru ratings into a Database, which thereby require a PrimaryKey
* The TourRating PK is a composed PK = tourId_customerId
*
*
* Q. Is there a difference between using the @Id and @EmbeddedId annotations when referencing a compound key?
* Sol: The @EmbeddedId and @IdClass annotations are used to denote composite primary keys
 */

/*
-------------------------------
id      tourId      customerId      score       comment
--------------------------------------------------------

 */
@Document
@Data
@AllArgsConstructor
public class TourRating {
    /*
    *
    * pk tourId_customerId // Due to the absence of ReferentialIntegrity in NoSQL, we cant join these tables anymore to get the <pk>
    * score 1-5
    * comment   string max 255characters
     */
    @Id
    private String id;

    private String tourId;

    @NotNull
    private String customerId;

    // We cant use the RatingDto anymore as it also having ReferentialIntegrity with table <Tour>;
    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)// can be nullable // commentLength <=255 characters
    private String comment;

    // constructor
    public TourRating(String tourId, String customerId, Integer score, String comment) {
        this.tourId = tourId;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }
}
