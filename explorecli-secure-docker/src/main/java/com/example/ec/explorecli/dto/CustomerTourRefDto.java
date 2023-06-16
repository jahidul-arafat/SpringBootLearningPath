package com.example.ec.explorecli.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTourRefDto extends RepresentationModel<CustomerTourRefDto> {
    @NotNull
    @PositiveOrZero(message = "Customer ID must be non-negative")
    private Integer CustomerId;

    @NotNull
    @PositiveOrZero(message = "Tour ID must be non-negative")
    private Long TourId;

    private String comment;
}
