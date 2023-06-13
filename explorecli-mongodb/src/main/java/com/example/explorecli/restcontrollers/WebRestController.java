package com.example.explorecli.restcontrollers;

import com.example.explorecli.domain.Tour;
import com.example.explorecli.domain.TourPackage;
import com.example.explorecli.services.TourPackageService;
import com.example.explorecli.services.TourService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WebRestController {
    private final TourPackageService tourPackageService;
    private final TourService tourService;

    // Rest-endpoint-1: Get all the available tour Packages
    @GetMapping("/packages")
    public Iterable<TourPackage> getAllTourPackages(){
        Iterable<TourPackage> tourPackageIterable = tourPackageService.lookup();
        return tourPackageIterable;
    }

    // RestEndpoint02: Get the tourPackage Details by the tourPackage <code>
    @GetMapping("/packages/{code}")
    public Optional<TourPackage> getTourPkgDetailsByCode(@PathVariable(value = "code") String code){
        return tourPackageService.getTourPkgDetailsByCode(code);

    }

    // method to get all tours
    @GetMapping("/tours")
    public Iterable<Tour> getAllTours(){
        return tourService.lookup();
    }

    // method to find summary by tour package code
    @GetMapping("/tours/summary/{code}")
    public List<Tour> findSummaryByTourPackageCode(@PathVariable(value = "code") String code){
        return tourService.findSummaryByTourPackageCode(code);
    }
}
