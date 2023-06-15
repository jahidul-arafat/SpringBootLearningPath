package com.example.ec.explorecli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public enum Difficulty {
    Easy("Easy"),
    Medium("Medium"),
    Difficult("Difficult"),
    Varies("Varies");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }

    // method to find difficulty by name
    public static Difficulty findDifficultyByName(String name) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.name.equals(name)) {
                return difficulty;
            }
        }
        return null;
    }

}
