package com.example.ec.explorecli.dto;

import com.example.ec.explorecli.domain.Tour;
import lombok.Data;

@Data
public class TourDto {
    // t.id, t.price, t.duration, t.tourPackage.code
    private Long id;
    private Double price;
    private String duration;
    private String tourPackageCode;

    // @Query("SELECT new TourDto(t.id, t.price, t.duration, t.tourPackage.code) " +
    //        "FROM Tour t WHERE t.price > :price")

    // constructor
    public TourDto(Tour t) {
        this.id = t.getId();
        this.price = t.getPrice();
        this.duration = t.getDuration();
        this.tourPackageCode = t.getTourPackage().getCode();
    }
}
