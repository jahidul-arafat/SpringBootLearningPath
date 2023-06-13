package com.example.explorecli.services;

import com.example.explorecli.domain.TourPackage;
import com.example.explorecli.repositories.TourPackageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* Create the Following Business Services
* 1. Create a tourPackage                   createTourPackage()
* 2. Find all the tourPackage             lookup()    // findAll()
* 3. Number of tourPackages available       total()     // count()
*
 */
@Service
@AllArgsConstructor
public class TourPackageService {
    private final TourPackageRepository tourPackageRepository;



    // Business Service-01
    // Create a Tour package
    // .findById()
    // .save()
    public TourPackage createTourPackage(String pkgCode, String pkgName){
        // check tourRepository to see if the pkgCode already exists, return that package
        // Else create a new tourPackage with the pkgCode and pkgName and save it into the repository (exposing the Database)
        return tourPackageRepository.findById(pkgCode)
                .orElse(tourPackageRepository.save(new TourPackage(pkgCode,pkgName)));
    }


    // Business Service-02
    // Find all the tour packages
    // .findAll() --> always returns Iterable, not a List
    public Iterable<TourPackage> lookup(){
        return this.tourPackageRepository.findAll();
    }

    // Business Service-03
    // return number of tourPackages found in the repository
    // .count() --> always return a long, not an Integer
    public long total(){
        return tourPackageRepository.count();

    }

    // Business Service-04
    // Get the tour Package details by code
    public Optional<TourPackage> getTourPkgDetailsByCode(String pkgCode){
        return tourPackageRepository.findById(pkgCode);
    }

}
