package com.example.ec.explorecli.util;

import com.example.ec.explorecli.domain.Difficulty;
import com.example.ec.explorecli.domain.Region;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * "packageType": "Taste of California",
 *     "title": "Oranges & Apples Tour",
 *     "blurb": "This three day tour is a taste of some of California's finest fruits. The first day features apple picking, a smoking barbeque and a hayride at a small farm in the historic Oak Glen, and the second take you'll drive to sample the only growing place in the world for pixie tangerines, Ojai, California.",
 *     "description": "Oak Glen is a tiny hamlet located in the foothill of the San Bernadino Mountain, only 90 minutes from LA, but the climate makes it perfect for stone fruits, such as pears, peaches, and nectarines. However, the jewel of this town is the apple. You'll start the day at Riley's Apple Farm, where you can pick and press as many apples as you can carry. Take a hayride around the farm property and, a fun treat for kids, visit the barn animals. Feast on apple-wood smoked BBQ featuring tri-tip, ribs and chicken apple sausage. Lodging is provided at the quaint Craftin Hills Inn. The next day, it's a short backwoods drive to Ojai, CA, where you'll receive a private tour and picnic lunch at the Churchill Brenneis Orchard. Owners Jim Churchill and Lisa Brenneis are knowledgeable and warm, and you'll leave with a glow and the most delicious tiny tangerines you've ever tasted. Spend a restful night at the Lavender Inn in beautiful downtown Ojai before returning to the regular pace.",
 *     "bullets": "Apple picking and cider pressing, Hayrides around the apple farm, Lunch with apple-wood smoked BBQ, Lodging at two distinct bed and breakfast inns, Walkabout through Ojai's fragrant orange groves",
 *     "difficulty": "Easy",
 *     "length": "3 days",
 *     "price": "350",
 *     "region": "Southern California",
 *     "keywords": "Tasting, Olive Oil, California History, Picnic, Nature, Farming"
 */
@Data
public class TourFromFile {
    private String packageType, title, blurb, description, bullets, difficulty, length, price, region, keywords;

    // method to load the tours from a json file with json auto detect
    /*
    * the code reads the bytes from the JSON file, creates an ObjectMapper instance, and then uses
    * it to deserialize the JSON data into a List<TourFromFile> object.
    * Finally, it returns the deserialized list.
    * */
    /*
    * Deserialization refers to the process of converting data in a specific format, such as JSON or XML, into an object representation in a programming language.
    * In the context of the code snippet you provided, deserialization is the process of converting
    * the JSON data read from the file into a List<TourFromFile> object.
    *
    *
    * */
    public static List<TourFromFile> loadToursFromJson(String fileToImport) throws IOException {
        return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
    }

    // getter
    public Difficulty getDifficulty() {
        return Difficulty.valueOf(difficulty);
    }

    public Region getRegion() {
        return Region.valueOf(region);
    }
}
