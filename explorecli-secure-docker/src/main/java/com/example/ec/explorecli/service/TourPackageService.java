package com.example.ec.explorecli.service;

import com.example.ec.explorecli.domain.TourPackage;
import com.example.ec.explorecli.repo.TourPackageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    // constructor injection as part of dependency injection
    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    // create a new tour package; first check if tour package already exists
    public TourPackage createTourPackage(String code, String name) {
        return tourPackageRepository.findById(code)
                .orElse(tourPackageRepository.save(new TourPackage(code,name)));
    }

    // method to return all tour packages
    public Iterable<TourPackage> getAllTourPackages() {
        return tourPackageRepository.findAll();
    }

    // method to count total tour packages
    public long countTourPackages() {
        return tourPackageRepository.count();
    }

    // method to find a tour package by name
    public Optional<TourPackage> findTourPackageByName(String name) {
        return tourPackageRepository.findByName(name);
    }

    // methid to find a tour package by code
    public TourPackage findTourPackageByCode(String code) {
        return tourPackageRepository.findById(code).orElse(null);
    }

    public void deleteTourPackage(String code) {
        tourPackageRepository.deleteById(code);
    }

    public TourPackage updateTourPackage(String code, String name) {
        return tourPackageRepository.findById(code).map(tourPackage -> {
            tourPackage.setName(name);
            return tourPackageRepository.save(tourPackage);
        }).orElse(null);
    }
}