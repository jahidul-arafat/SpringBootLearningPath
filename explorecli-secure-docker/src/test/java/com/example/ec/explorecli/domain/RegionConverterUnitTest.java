package com.example.ec.explorecli.domain;

import com.example.ec.explorecli.db_entity_converters.RegionConverter;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RegionConverterUnitTest {

    private RegionConverter regionConverter = new RegionConverter();
    @Test // Junit 4
    public void testConvertToDatabaseColumn() {
        // expected (String), actual (String), message
        assertEquals(Region.Central_Coast.getName(),
                regionConverter.convertToDatabaseColumn(Region.Central_Coast), "Checking the name");
        assertEquals(Region.Northern_California.getName(),
                regionConverter.convertToDatabaseColumn(Region.Northern_California), "Checking the name");
        assertEquals(Region.Southern_California.getName(),
                regionConverter.convertToDatabaseColumn(Region.Southern_California), "Checking the name");
        assertEquals(Region.Varies.getName(),
                regionConverter.convertToDatabaseColumn(Region.Varies), "Checking the name");
    }

    @Test
    public void testConvertToEntityAttribute() {
        // expected (An Object), actual (An Object), message
        assertEquals(Region.Central_Coast,
                regionConverter.convertToEntityAttribute(Region.Central_Coast.getName()));
        assertEquals(Region.Northern_California,
                regionConverter.convertToEntityAttribute(Region.Northern_California.getName()));
        assertEquals(Region.Southern_California,
                regionConverter.convertToEntityAttribute(Region.Southern_California.getName()));
        assertEquals(Region.Varies,
                regionConverter.convertToEntityAttribute(Region.Varies.getName()));

    }
}
