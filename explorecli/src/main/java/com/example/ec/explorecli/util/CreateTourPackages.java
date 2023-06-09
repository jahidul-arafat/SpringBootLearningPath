package com.example.ec.explorecli.util;

import com.example.ec.explorecli.service.TourPackageService;
import org.springframework.stereotype.Component;

@Component
public class CreateTourPackages {
    private final TourPackageService tourPackageService;
    public CreateTourPackages(TourPackageService tourPackageService) {
        this.tourPackageService = tourPackageService;
    }
    // method to create tour packages
    public void createTourPackage() {
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }
}
