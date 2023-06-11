package com.example.ec.explorecli.domain;

public enum Difficulty {
    Easy("Easy"),
    Medium("Medium"),
    Difficult("Difficult"),
    Varies("Varies");

    private String name;

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
