package com.example.explorecli.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

// /tours would only show a summary (id, title, tourPackageCode, tourPackageName)
/*
----------------------------------------------------------------
id      title   tourPackageCode     tourPackageName     details
----------------------------------------------------------------
 */
@Document   // An Entity of A Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

    // A. Tour Main Attributes
    @Id
    private String id;     // MongoDB Private Key must be String

    @Indexed        // Why Indexed is placed here? Because whenever we gonna be searching using <Title> it gonna be faster
    private String title;

    // Attributes: description, blurb, price and duration removed

    // Multiple Tour could be in a Single TourPackage
    // New Attribute in the NoSQL Table <Tour>
    @Indexed    // indexed to make this faster when search with tourPackageCode
    private String tourPackageCode; // this gonna be duplication as tourPakcgeCode is already there in the @Document TourPackage.
                                    // Since there is no referential integration available in NoSQL, we had to embed this tourPackageCode in the
                                    // Herce, NoSQL doesnt support referential integrity, we cant Map @ManyToOne relationwhip between Tour <<>>TourPackage
                                    // Thats why duplicate embedding of tourPackageCode is placed in the @Document/@Entity Tour


    // New Attribute in the NoSQL Table <Tour>
    private String tourPackageName;

    // B. Tour Details Attributes
    // Now as place description, blurb, price, duration, bullets, keywords,regions,difficulty under a Map object
    private Map<String, String> details;


    // Constructor
    // this is not an AllArgument Constructor
    // The main purpose of creating this constructor is to create a new Tour in the TourService
    // this constructor doesnt have the <id>, as this gonna be auto-generated

    public Tour(String title, TourPackage tourPackage, Map<String, String> details) {
        this.title = title;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
        this.details = details;
    }

}
