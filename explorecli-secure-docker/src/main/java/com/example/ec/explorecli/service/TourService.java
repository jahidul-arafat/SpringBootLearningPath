package com.example.ec.explorecli.service;

import com.example.ec.explorecli.domain.Difficulty;
import com.example.ec.explorecli.domain.Region;
import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.domain.TourPackage;
import com.example.ec.explorecli.dto.TourDto;
import com.example.ec.explorecli.repo.TourPackageRepository;
import com.example.ec.explorecli.repo.TourRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    // constructor-injection as part of dependency injection
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    // method to create a new tour with all arguments; get the tour attributes from Tour class and create a new tour package

    /**
     * @param title
     * @param description
     * @param blurb
     * @param price
     * @param duration
     * @param bullets
     * @param keywords
     * @param tourPackageName
     * @param difficulty
     * @param region
     * @return
     */
    public Tour createTour(String title, String description, String blurb, Double price,
                           String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region) {

        TourPackage existingTourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() ->
                        new RuntimeException("TourPackage not found: " + tourPackageName));
        return tourRepository.save(new Tour(existingTourPackage, title, blurb, description,
                bullets, difficulty, duration, price, region, keywords));
    }

    // method to get all the tours in the database
    public List<Tour> getAllTours() {
        return (List<Tour>) tourRepository.findAll();
    }

    // method to count total number of tours in the database
    public long countTours() {
        return tourRepository.count();
    }

    public void getTourDetailsByTourPackageCode() {
        List<Object[]> tourDetails = tourRepository.findTourDetailsByTourPackageCode();
        for (Object[] tourDetail : tourDetails) {
            System.out.println(Arrays.toString(tourDetail));
        }
    }

    // find tour by tour package code
    public List<Tour> getTourByTourPackageCode(String tourPackageCode) {
        return tourRepository.findByTourPackageCode(tourPackageCode);
    }

    // method to get a tour by id
    public Optional<Tour> getTourById(Long id) {
        return tourRepository.findById(id);
    }

    // find a tour by title
    public Optional<Tour> getTourByTitle(String title) {
        return tourRepository.findByTitle(title);
    }

    // find tours by difficulty
    public List<Tour> getToursByDifficulty(String difficulty) {
        Difficulty d = Arrays.stream(Difficulty.values())
                .filter(e-> e.name().equals(difficulty))
                .findFirst()
                .orElse(Difficulty.Varies);

        return tourRepository.findByDifficulty(d);

    }


    // find tours by region
    public List<Tour> getToursByRegion(String region) {
        Region r = Arrays.stream(Region.values())
                .filter(e-> e.name().equals(region))
                .findFirst()
                .orElse(Region.Varies);

        return tourRepository.findByRegion(r);
    }

    // find tours less than a certain price
    public List<TourDto> getToursByPriceLessThan(double price) {
        return tourRepository.findByPriceLessThan(price);
    }

    // method to find tours by price greater than a certain price and less than a certain price
    public List<TourDto> getToursByPriceGreaterThanAndPriceLessThan(double price1, double price2) {
        return tourRepository.findByPriceGreaterThanAndPriceLessThan(price1, price2);
    }

    // find tours by tour package code and bullets
    public List<Tour> getToursByTourPackageCodeAndBulletsLike(String tourPackageCode, String bullets) {
        return tourRepository.findByTourPackageCodeAndBulletsLike(tourPackageCode, bullets);
    }

    // add a tour to the database
    public void addTour(Tour tour) {
        if (null ==tour)
            throw new RuntimeException("Tour cannot be null");
        tourRepository.save(tour);
    }



}
