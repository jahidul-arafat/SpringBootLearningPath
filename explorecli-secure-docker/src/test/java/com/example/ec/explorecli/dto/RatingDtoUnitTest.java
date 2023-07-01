package com.example.ec.explorecli.dto;

import jakarta.transaction.Transactional;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class RatingDtoUnitTest {
    private static final Integer CUSTOMER_ID = 100;
    private static final Integer SCORE = 5;
    private static final String COMMENT = "Dummy Comment";
    @Test
    public void testConstructorObject() {
        RatingDto ratingDto = new RatingDto(SCORE, COMMENT, CUSTOMER_ID);
        assertNotNull(ratingDto);
        assertEquals(SCORE, ratingDto.getScore());
        assertEquals(COMMENT, ratingDto.getComment());
        assertEquals(CUSTOMER_ID, ratingDto.getCustomerId());
    }

    @Test
    public void testConstructorObjectNull() {
        RatingDto ratingDto = new RatingDto();
        assertNotNull(ratingDto);
    }

    @Test
    public void testSetter(){
        RatingDto ratingDto = new RatingDto();
        ratingDto.setScore(SCORE);
        ratingDto.setComment(COMMENT);
        ratingDto.setCustomerId(CUSTOMER_ID);
        assertEquals(SCORE, ratingDto.getScore());
        assertEquals(COMMENT, ratingDto.getComment());
        assertEquals(CUSTOMER_ID, ratingDto.getCustomerId());
    }

}
