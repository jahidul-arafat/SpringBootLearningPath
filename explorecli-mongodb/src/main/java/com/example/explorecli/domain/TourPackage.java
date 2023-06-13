package com.example.explorecli.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
* Lombok are used
* Thereby no need for the followings
* - NoArgument Constructor
* - All Argument Constructor
* - ToString method
* - Getter
* - Setter
* - Object Equality and Hashing
* */
@Document
@Data
@AllArgsConstructor
public class TourPackage {

    @Id
    private String code; // this is not auto-generated; we have to provide it either in the POST method or in the PUT method

    private String name;




}
