package com.example.ec.explorecli.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DifficultyUnitTest {
    @Test
    public void findDifficultyByStringName() { // don't need to manually throw exceptions; assertions themselves can throw exceptions
        assertEquals(Difficulty.Easy, Difficulty.findDifficultyByName("Easy"));
        assertEquals(Difficulty.Medium, Difficulty.findDifficultyByName("Medium"));
        assertEquals(Difficulty.Difficult, Difficulty.findDifficultyByName("Difficult"));
        assertEquals(Difficulty.Varies, Difficulty.findDifficultyByName("Varies"));
    }
    @Test
    public void findDifficultyStringByStringName() {
        /*Make sure, there are no Null Pointer Exceptions*/
        assertEquals("Easy", Difficulty.Easy.getName());
        assertEquals("Medium", Difficulty.Medium.getName());
        assertEquals("Difficult", Difficulty.Difficult.getName());
        assertEquals("Varies", Difficulty.Varies.getName());
    }

}
