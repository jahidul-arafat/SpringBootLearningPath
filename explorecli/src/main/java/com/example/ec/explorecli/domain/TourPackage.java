package com.example.ec.explorecli.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "TOUR_PACKAGE")
public class TourPackage {
    @Id
    private String code;        // primary key, but not autogenerated, user input requries

    @Column(name = "name")
    private String name;
}
