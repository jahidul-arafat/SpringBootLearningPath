package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.Difficulty;
import com.example.ec.explorecli.domain.Region;
import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourPackage;
import com.example.ec.explorecli.dto.TourDto;
import com.example.ec.explorecli.service.TourPackageService;
import com.example.ec.explorecli.service.TourService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
In general, it is recommended to use @Transactional at the service layer rather than the controller layer.
The service layer is responsible for handling business logic and interacting with the data layer,
making it a suitable place to manage transactions.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class WebRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);
    private final TourPackageService tourPackageService;
    private final TourService tourService;

    // method to get all tour packages
    @GetMapping("/packages")
    public Iterable<TourPackage> getAllTourPackages() {
        LOGGER.info("GET /api/packages");
        return tourPackageService.getAllTourPackages();
    }

    // method to get all tour packages by tour package code
    @GetMapping("/packages/{code}")
    public TourPackage findTourPackageByCode(@PathVariable(value = "code") String code) {
        LOGGER.info("GET /api/packages/{}",code);
        return tourPackageService.findTourPackageByCode(code);
    }

    // method to create a tour package by tour package code and name
    @PostMapping("/packages/{code}/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public TourPackage createTourPackage(@PathVariable(value = "code") String code,
                                         @PathVariable(value = "name") String name) {
        LOGGER.info("POST /api/packages/{}/{}",code,name);
        return tourPackageService.createTourPackage(code, name);
    }

    // method to delete a tour package by tour package code
    @DeleteMapping("/packages/{code}")
    public void deleteTourPackage(@PathVariable(value = "code") String code) {
        LOGGER.info("DELETE /api/packages/{}",code);
        tourPackageService.deleteTourPackage(code);
    }

    // method to count all tour packages
    @GetMapping("/packages/count")
    public long countTourPackages() {
        LOGGER.info("GET /api/packages/count");
        return tourPackageService.countTourPackages();
    }

    // method to update a tourpackage
    @PutMapping("/packages/{code}")
    public void updateTour(@PathVariable(value = "code") String code, @RequestBody String name) {
        LOGGER.info("PUT /api/packages/{}/{}",code,name);
        tourPackageService.updateTourPackage(code, name);
    }

    // method to get all tours
    @GetMapping("/tours")
    public Iterable<Tour> getAllTours() {
        LOGGER.info("GET /api/tours");
        return tourService.getAllTours();
    }

    // method to get a tour by tour id
    @GetMapping("/tours/id/{id}")
    public Optional<Tour> getTourById(@PathVariable(value = "id") Long id) {
        LOGGER.info("GET /api/tours/id/{}",id);
        return tourService.getTourById(id);
    }

    // method to get all tours by tour package code
    @GetMapping("/tours/{code}")
    public List<Tour> getTourByTourPackageCode(@PathVariable(value = "code") String code) {
        LOGGER.info("GET /api/tours/{}",code);
        return tourService.getTourByTourPackageCode(code);
    }

    // method to get all tours by difficulty
    @GetMapping("/tours/difficulty")
    public List<Tour> getToursByDifficulty(@RequestParam(value = "difficulty") String difficulty) {
        LOGGER.info("GET /api/tours/difficulty/{}",difficulty);
        return tourService.getToursByDifficulty(difficulty);
    }

    // method to get all tours by region
    @GetMapping("/tours/region")
    public List<Tour> getToursByRegion(@RequestParam(value = "region") String region) {
        LOGGER.info("GET /api/tours/region/{}",region);
        return tourService.getToursByRegion(region);
    }

    // method to get all tours by price less than
    @GetMapping("/tours/price/less")
    public List<TourDto> getToursByPriceLessThan(@RequestParam(value = "price") double price) {
        LOGGER.info("GET /api/tours/price/{}",price);
        return tourService.getToursByPriceLessThan(price);
    }

    // method to find tours by price greater than a certain price and less than a certain price
    @GetMapping("/tours/price/between")
    public List<TourDto> getToursByPriceBetween(@RequestParam(value = "price_min") double price_min,
                                                @RequestParam(value = "price_max") double price_max) {
        LOGGER.info("GET /api/tours/price/between/{}/{}",price_min,price_max);
        return tourService.getToursByPriceGreaterThanAndPriceLessThan(price_min, price_max);
    }

    // method to count all tours
    @GetMapping("/tours/count")
    public long countTours() {
        LOGGER.info("GET /api/tours/count");
        return tourService.countTours();
    }

    // method to add a tour
    @PostMapping("/tours")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTour(@RequestBody Tour tour) {
        LOGGER.info("POST /api/tours");
        tourService.addTour(tour);
    }





}
