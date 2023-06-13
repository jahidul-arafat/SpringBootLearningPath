package com.example.explorecli.util;

import com.example.explorecli.helpers.CreateTourPackages;
import com.example.explorecli.helpers.CreateTours;
import com.example.explorecli.services.TourPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(AppStartupEvent.class);
    private final CreateTourPackages createTourPackage;
    private final CreateTours createTour;
    private final TourPackageService tourPackageService;

    @Value("${explorecli.importfile}") // this is imported from resources/application.properties
    private String importFile;

    public AppStartupEvent(CreateTourPackages createTourPackage, CreateTours createTour, TourPackageService tourPackageService) {

        this.createTourPackage = createTourPackage;
        this.createTour = createTour;
        this.tourPackageService = tourPackageService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("---------Application started-----------");
        logger.debug("Debug message");
        logger.error("---------Error message/Custom/By Jahidul Arafat-----------");

        createTourPackage.createTourAllPackages();

        try {
            createTour.createTours(importFile);
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
