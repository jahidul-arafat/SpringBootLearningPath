package com.example.ec.explorecli.util;

import com.example.ec.explorecli.service.TourService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateTours {
    /*
    *
    * createTour(String title, String description, String blurb, String price,
                           String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region)
    * */
    private final TourService tourService;

    public CreateTours(TourService tourService) {
        this.tourService = tourService;
    }

    public void createTour(String fileToImport) throws IOException {
        TourFromFile.loadToursFromJson(fileToImport).forEach(importedTour -> {
            tourService.createTour(importedTour.getTitle(),
                    importedTour.getDescription(),
                    importedTour.getBlurb(),
                    Double.parseDouble(importedTour.getPrice()),
                    importedTour.getLength(),
                    importedTour.getBullets(),
                    importedTour.getKeywords(),
                    importedTour.getPackageType(),
                    importedTour.getDifficulty(),
                    importedTour.getRegion());
        });
    }
}
