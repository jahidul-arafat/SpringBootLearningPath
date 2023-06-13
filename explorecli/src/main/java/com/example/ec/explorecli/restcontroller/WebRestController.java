package com.example.ec.explorecli.restcontroller;

import com.example.ec.explorecli.domain.Difficulty;
import com.example.ec.explorecli.domain.Region;
import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourPackage;
import com.example.ec.explorecli.service.TourPackageService;
import com.example.ec.explorecli.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class WebRestController {
    private final TourPackageService tourPackageService;
    private final TourService tourService;

    // method to get all tour packages
    @GetMapping("/packages")
    public Iterable<TourPackage> getAllTourPackages() {
        return tourPackageService.getAllTourPackages();
    }

    // method to get all tour packages by tour package code
    @GetMapping("/packages/{code}")
    public TourPackage findTourPackageByCode(@PathVariable(value = "code") String code) {
        return tourPackageService.findTourPackageByCode(code);
    }

    // method to create a tour package by tour package code and name
    @PostMapping("/packages/{code}/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public TourPackage createTourPackage(@PathVariable(value = "code") String code, @PathVariable(value = "name") String name) {
        return tourPackageService.createTourPackage(code, name);
    }

    // method to delete a tour package by tour package code
    @DeleteMapping("/packages/{code}")
    public void deleteTourPackage(@PathVariable(value = "code") String code) {
        tourPackageService.deleteTourPackage(code);
    }

    // method to count all tour packages
    @GetMapping("/packages/count")
    public long countTourPackages() {
        return tourPackageService.countTourPackages();
    }

    // method to update a tourpackage
    @PutMapping("/packages/{code}")
    public void updateTour(@PathVariable(value = "code") String code, @RequestBody String name) {
        tourPackageService.updateTourPackage(code, name);
    }

    // method to get all tours
    @GetMapping("/tours")
    public Iterable<Tour> getAllTours() {
        return tourService.getAllTours();
    }

    // method to get all tours by tour package code
    @GetMapping("/tours/{code}")
    public List<Tour> getTourByTourPackageCode(@PathVariable(value = "code") String code) {
        return tourService.getTourByTourPackageCode(code);
    }

    // method to get all tours by difficulty
    @GetMapping("/tours/difficulty")
    public List<Tour> getToursByDifficulty(@RequestParam(value = "difficulty") String difficulty) {
        return tourService.getToursByDifficulty(difficulty);
    }

    // method to get all tours by region
    @GetMapping("/tours/region")
    public List<Tour> getToursByRegion(@RequestParam(value = "region") String region) {
        return tourService.getToursByRegion(region);
    }

    // method to get all tours by price less than
    @GetMapping("/tours/price")
    public List<Tour> getToursByPriceLessThan(@RequestParam(value = "price") double price) {
        return tourService.getToursByPriceLessThan(price);
    }

    // method to count all tours
    @GetMapping("/tours/count")
    public long countTours() {
        return tourService.countTours();
    }

    // method to add a tour
    @PostMapping("/tours")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTour(@RequestBody Tour tour) {
        tourService.addTour(tour);
    }





}
