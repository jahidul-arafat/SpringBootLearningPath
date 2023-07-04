package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.Difficulty;
import com.example.ec.explorecli.domain.Region;
import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourPackage;
import com.example.ec.explorecli.dto.TourDto;
import com.example.ec.explorecli.service.TourPackageService;
import com.example.ec.explorecli.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tour", description = "Tour API")
public class WebRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);
    private final TourPackageService tourPackageService;
    private final TourService tourService;

    // method to get all tour packages
    @GetMapping("/packages")
    @Operation(summary = "Get all tour packages")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public Iterable<TourPackage> getAllTourPackages() {
        LOGGER.info("GET /api/packages");
        return tourPackageService.getAllTourPackages();
    }

    // method to get all tour packages by tour package code
    @GetMapping("/packages/{code}")
    @Operation(summary = "Find Tour Package by code :: Codes [BC, CC, etc]")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "TourPackage Code not found")})
    public TourPackage findTourPackageByCode(@PathVariable(value = "code") String code) {
        LOGGER.info("GET /api/packages/{}",code);
        return tourPackageService.findTourPackageByCode(code);
    }

    // method to create a tour package by tour package code and name
    @PostMapping("/packages/{code}/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Tour Package by code and name")
    public TourPackage createTourPackage(@PathVariable(value = "code") String code,
                                         @PathVariable(value = "name") String name) {
        LOGGER.info("POST /api/packages/{}/{}",code,name);
        return tourPackageService.createTourPackage(code, name);
    }

    // method to delete a tour package by tour package code
    @DeleteMapping("/packages/{code}")
    @Operation(summary = "Delete Tour Package by code")
    public void deleteTourPackage(@PathVariable(value = "code") String code) {
        LOGGER.info("DELETE /api/packages/{}",code);
        tourPackageService.deleteTourPackage(code);
    }

    // method to count all tour packages
    @GetMapping("/packages/count")
    @Operation(summary = "Count all tour packages")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public long countTourPackages() {
        LOGGER.info("GET /api/packages/count");
        return tourPackageService.countTourPackages();
    }

    // method to update a tourpackage
    @PutMapping("/packages/{code}")
    @Operation(summary = "Update Tour Package by code")
    public void updateTour(@PathVariable(value = "code") String code, @RequestBody String name) {
        LOGGER.info("PUT /api/packages/{}/{}",code,name);
        tourPackageService.updateTourPackage(code, name);
    }

    // method to get all tours
    @GetMapping("/tours")
    @Operation(summary = "Get all tours")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public Iterable<Tour> getAllTours() {
        LOGGER.info("GET /api/tours");
        return tourService.getAllTours();
    }

    // method to get a tour by tour id
    @GetMapping("/tours/id/{id}")
    @Operation(summary = "Get a tour by tour id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "TourId not found")})
    public Optional<Tour> getTourById(@PathVariable(value = "id") Long id) {
        LOGGER.info("GET /api/tours/id/{}",id);
        return tourService.getTourById(id);
    }

    // method to get all tours by tour package code
    @GetMapping("/tours/{code}")
    @Operation(summary = "Get List of tours by tour package code")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Tour package code not found")})
    public List<Tour> getTourByTourPackageCode(@PathVariable(value = "code") String code) {
        LOGGER.info("GET /api/tours/{}",code);
        return tourService.getTourByTourPackageCode(code);
    }

    // method to get all tours by difficulty
    @GetMapping("/tours/difficulty")
    @Operation(summary = "Get List of tours by difficulty")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Mentioned Difficulty level not found")})
    public List<Tour> getToursByDifficulty(@RequestParam(value = "difficulty") String difficulty) {
        LOGGER.info("GET /api/tours/difficulty/{}",difficulty);
        return tourService.getToursByDifficulty(difficulty);
    }

    // method to get all tours by region
    @GetMapping("/tours/region")
    @Operation(summary = "Get List of tours by region")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Mentioned Region not found")})
    public List<Tour> getToursByRegion(@RequestParam(value = "region") String region) {
        LOGGER.info("GET /api/tours/region/{}",region);
        return tourService.getToursByRegion(region);
    }

    // method to get all tours by price less than
    @GetMapping("/tours/price/less")
    @Operation(summary = "Get List of tours by price less than")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<TourDto> getToursByPriceLessThan(@RequestParam(value = "price") double price) {
        LOGGER.info("GET /api/tours/price/{}",price);
        return tourService.getToursByPriceLessThan(price);
    }

    // method to find tours by price greater than a certain price and less than a certain price
    @GetMapping("/tours/price/between")
    @Operation(summary = "Get List of tours by price in between")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<TourDto> getToursByPriceBetween(@RequestParam(value = "price_min") double price_min,
                                                @RequestParam(value = "price_max") double price_max) {
        LOGGER.info("GET /api/tours/price/between/{}/{}",price_min,price_max);
        return tourService.getToursByPriceGreaterThanAndPriceLessThan(price_min, price_max);
    }

    // method to count all tours
    @GetMapping("/tours/count")
    @Operation(summary = "Count all tours")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public long countTours() {
        LOGGER.info("GET /api/tours/count");
        return tourService.countTours();
    }

    // method to add a tour
    @PostMapping("/tours")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a tour")
    public void addTour(@RequestBody Tour tour) {
        LOGGER.info("POST /api/tours");
        tourService.addTour(tour);
    }





}
