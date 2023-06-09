package com.example.ec.explorecli.util;

import com.example.ec.explorecli.service.TourPackageService;
import com.example.ec.explorecli.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(AppStartupEvent.class);
    private final TourService tourService;
    private final TourPackageService tourPackageService;
    private final CreateTourPackages createTourPackage;
    private final CreateTours createTour;

    @Value("${explorecli.importfile}") // this is imported from resources/application.properties
    private String importFile;

    public AppStartupEvent(TourService tourService, TourPackageService tourPackageService,
                           CreateTourPackages createTourPackage, CreateTours createTour) {
        this.tourService = tourService;
        this.tourPackageService = tourPackageService;
        this.createTourPackage = createTourPackage;
        this.createTour = createTour;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Log messages
        logger.info("---------Application started-----------");
        logger.debug("Debug message");
        logger.error("---------Error message-----------");

        createTourPackage.createTourPackage();
        try {
            createTour.createTour(importFile);
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        System.out.printf("Total Tour Packages in Database: %d\n", tourPackageService.countTourPackages());
        System.out.printf("Total Tours in Database: %d\n", tourService.countTours());

        System.out.println("Get all tour packages");
        tourPackageService.getAllTourPackages()
                .forEach(System.out::println);

        System.out.println("Get all tours");
        tourService.getAllTours()
                .forEach(System.out::println);

        // get the tour details from TourService getTourDetailsByTourPackageCode
        System.out.println("Get all tour details");
        tourService.getTourDetailsByTourPackageCode();

        // find tour by tour package code
        Scanner scanner = new Scanner(System.in);
        System.out.print("Find tour by tour package code: ");
        var tourPackageCode = scanner.nextLine();
        tourService.getTourByTourPackageCode(tourPackageCode)
                .forEach(System.out::println);

        // find tour by title
        System.out.print("Find tour by title: ");
        var title = scanner.nextLine();
        System.out.println(tourService.getTourByTitle(title));

        // find tour by difficulty
        System.out.print("Find tour by difficulty: ");
        var difficulty = scanner.nextLine();
        tourService.getToursByDifficulty(difficulty);

        // find tour by region
        System.out.print("Find tour by region: ");
        var region = scanner.nextLine();
        tourService.getToursByRegion(region).forEach(System.out::println);

        // find tours less than price
        System.out.print("Find tours less than price: ");
        double price;
        try {
            price = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Using default value of 100.");
            price = 100.0;
        }
        tourService.getToursByPriceLessThan(price).forEach(System.out::println);


    }
}
