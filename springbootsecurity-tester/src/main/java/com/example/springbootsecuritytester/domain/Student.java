package com.example.springbootsecuritytester.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
}
