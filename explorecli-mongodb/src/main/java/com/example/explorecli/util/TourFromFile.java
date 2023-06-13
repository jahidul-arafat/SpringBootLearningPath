package com.example.explorecli.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class TourFromFile {
    // Creating a new Object Type: TourFromFile, which reassemble the Domain/Model: Tour
    // This TourFromFile will read the JSON input file where customer provides us all the tour information and attributes
    // We will read that file and create a new Tour
    private String title, packageName;
    Map<String, String> details;

    // constructor
    public TourFromFile(Map<String, String> record) {
        this.title=record.get("title");
        this.packageName=record.get("packageType");
        this.details=record;    // all except "title" and "packageType"
        this.details.remove("title");
        this.details.remove("packageType");
    }

    // Class Method
    // Why you are returning TourFromFile object type instead directly returning Tour
    // Because, there tour packageType read as String from the JSON file, Tour required packageType to be TourPackage, not String
    // Even tour service when creating a tour require the packageType to be in String format
    // it then check if that package is already available, else it creates a new tourPackage object with that String packageType
    // and then finally trigger the createTour
    public static List<TourFromFile> loadToursFromJson(String fileToImport) throws IOException {
        List<Map<String,String>> records = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .readValue(new FileInputStream(fileToImport), new TypeReference<List<Map<String,String>>>() {});
        return records
                .stream()
                .map(TourFromFile::new)
                .collect(Collectors.toList());


    }


}
