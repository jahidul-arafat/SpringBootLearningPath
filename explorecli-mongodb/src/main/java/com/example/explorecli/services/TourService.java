package com.example.explorecli.services;

import com.example.explorecli.domain.Tour;
import com.example.explorecli.domain.TourPackage;
import com.example.explorecli.repositories.TourPackageRepository;
import com.example.explorecli.repositories.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
* TourService
* ------------
* 1 - Create a new tour and add that tour into the tourPackage, createTour()
* 2 - Calculate the number of tours in the database
*
*
* */
@Service
@AllArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    // Business Service -01
    // Create a new tour
    // first check if the tour to be created is already in the Database; if so, dont create; just return that tourObject
    // else create the tourObject and save it into the repository
    // Creating a Tour requires the following attributes

    /**
     *
     * @param title
     * @param tourPackageName
     * @return Tour Entity
     */


    public Tour createTour(String title, String tourPackageName, Map<String, String> details){
        // One this to note, a prerequisite of a Tour to be created is already having a TourPackage
        // A Tour cant live without a TourPackage

        // So, first check, whether the tourPackage of the 'tourPackageName' really exists
        // if so, return that tourPackage
        // else, a RunTime exception will arise
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(()->
                new RuntimeException("Tour package does not exist: "+tourPackageName));

        // Second phase, if the tourPackage already exists, that create the tour with the tourPackage
        // Tour having a 'Many-to-One' relationship with 'TourPackage'
        // Multiple Tours can be under a single TourPackage
        return tourRepository.save(
                new Tour(title,
                        tourPackage,
                        details));
    }


    // Business Service -02
    // Get all the tour lists available
    public Iterable<Tour> lookup(){
        return tourRepository.findAll();
    }
    // Business Service -02
    // Count the total number of tours in the database
    // JpaRepository  query will return a 'long' instead 'int'
    // .count
    public long total(){
        return tourRepository.count();
    }

    // method to find Summary By TourPackageCode
    public List<Tour> findSummaryByTourPackageCode(String code){
        return tourRepository.findSummaryByTourPackageCode(code);
    }





}
