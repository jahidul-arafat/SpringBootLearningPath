package com.example.explorecli.helpers;

import com.example.explorecli.util.TourFromFile;
import com.example.explorecli.services.TourService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateTours {

    private final TourService tourService;

    public CreateTours(TourService tourService) {
        this.tourService = tourService;
    }

    public void createTours(String fileToImport) throws IOException {
        TourFromFile.loadToursFromJson(fileToImport).forEach(importedTour ->
                tourService.createTour(
                        importedTour.getTitle(),
                        importedTour.getPackageName(),
                        importedTour.getDetails()));
    }

}
