package com.example.ec.explorecli.db_entity_converters;

import com.example.ec.explorecli.domain.Region;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Converter(autoApply = true)
public class RegionConverter implements AttributeConverter<Region, String> {

    @Override
    public String convertToDatabaseColumn(Region region) {
        return region.getName();
    }

    @Override
    public Region convertToEntityAttribute(String s) {
        return Region.findByName(s);
    }
}
