package com.example.ec.explorecli.dto;

import com.example.ec.explorecli.domain.TourRating;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

// Rating a Particular Tour; means in the RESTFul URL http://localhost:8080/api/{tourId}/ratings ;
// see the {tourId} is already there
// this DTO will be passed in the Request Body
/*
{
    "score":5,
    "comment": "Amazing tour",
    "customerId": 123
}
 */
/*
By extending RepresentationModel<RatingDto>,
the RatingDto class becomes a representation model
that can hold links and other resources related to the rating.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto extends RepresentationModel<RatingDto> {
    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    @PositiveOrZero(message = "Customer ID must be non-negative")
    private Integer customerId;


    // crate a constructor for the DTO using tourRating
    public RatingDto(TourRating tourRating) {
        this.score = tourRating.getScore();
        this.comment = tourRating.getComment();
        this.customerId = tourRating.getPk().getCustomerID();
    }
}
