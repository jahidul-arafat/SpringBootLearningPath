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
        Scanner sc = new Scanner(System.in);
        String choice;
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

        //terminalSimulator(sc);


    }

    private void terminalSimulator(Scanner sc) {
        String choice;
        while (true){

            System.out.println("Welcome to ExploreCLI!");
            System.out.println("1. Total packages in the database" +
                    "\n2. Total tours in the database" +
                    "\n3. Get all tour packages" +
                    "\n4. Get all tour" +
                    "\n5. Find tour by tour package code" +
                    "\n6. Find tour by title" +
                    "\n7. Find tour by difficulty" +
                    "\n8. Find tour by region" +
                    "\n9. Find tours less than price" +
                    "\n10.Find tours by tour package code and bullets like" +
                    "\n11. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = sc.next();
            System.out.println();

            switch (choice) {
                case "1" -> System.out.printf("Total Tour Packages in Database: %d\n",
                        tourPackageService.countTourPackages());
                case "2" -> System.out.printf("Total Tours in Database: %d\n",
                        tourService.countTours());
                case "3" -> tourPackageService.getAllTourPackages()
                        .forEach(System.out::println);
                case "4" -> tourService.getAllTours()
                        .forEach(System.out::println);
                case "5" -> {
                    System.out.print("Find tour by tour package code: ");
                    sc.nextLine();
                    var tourPackageCode = sc.next();
                    tourService.getTourByTourPackageCode(tourPackageCode)
                            .forEach(System.out::println);
                }
                case "6" -> {
                    System.out.print("Find tour by title: ");
                    sc.nextLine();
                    var title = sc.nextLine();
                    System.out.println(tourService.getTourByTitle(title));
                }
                case "7" -> {
                    System.out.print("Find tour by difficulty: ");
                    sc.nextLine();
                    var difficulty = sc.next();
                    tourService.getToursByDifficulty(difficulty);
                }
                case "8" -> {
                    System.out.print("Find tour by region: ");
                    sc.nextLine();
                    var region = sc.nextLine();
                    tourService.getToursByRegion(region).forEach(System.out::println);
                }
                case "9" -> {
                    System.out.print("Find tours less than price: ");
                    double price;
                    sc.nextLine();
                    try {
                        price = sc.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Using default value of 100.");
                        price = 100.0;
                    }
                    tourService.getToursByPriceLessThan(price).forEach(System.out::println);
                }
                case "10" -> {
                    System.out.println("Find tours by tour package code and bullets like");
                    sc.nextLine();
                    System.out.print("Enter tour package code: ");
                    var tourPkgCode = sc.next();
                    sc.nextLine();
                    System.out.print("Enter bullets like: ");
                    var bullets = sc.nextLine();
                    System.out.println(tourPkgCode+ " /// "+bullets);
                    tourService.getToursByTourPackageCodeAndBulletsLike(tourPkgCode, bullets)
                            .forEach(System.out::println);
                }
                default -> {
                    System.out.println("Invalid input. Exiting...");
                    return;
                }
            } // switch end
        } // while end
    }
}
