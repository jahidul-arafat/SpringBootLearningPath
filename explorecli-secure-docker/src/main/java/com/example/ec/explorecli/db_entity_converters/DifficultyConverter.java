package com.example.ec.explorecli.db_entity_converters;

import com.example.ec.explorecli.domain.Difficulty;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DifficultyConverter implements AttributeConverter<Difficulty, String> {

    @Override
    public String convertToDatabaseColumn(Difficulty difficulty) {
        return difficulty.getName();
    }

    @Override
    public Difficulty convertToEntityAttribute(String s) {
        return Difficulty.findDifficultyByName(s);
    }
}
