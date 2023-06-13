package com.example.explorecli.repositories;

import com.example.explorecli.domain.TourRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

//@Repository
@RepositoryRestResource(exported = false) // we dont want this Repository to be publicly exposed through RESTFul API Call
public interface TourRatingRepository extends CrudRepository<TourRating, String> {
    // list all tourRatings for a tour // by the touId (primary key of Tour Entity in persistent view)
    // Return type: List
    List<TourRating> findByTourId(String tourId);

    // Find the tourRating (Score and Comment) made by a specific customer to a tour
    //PkTourIdAndPkCustomerId
    // Return type is Optional
    Optional<TourRating> findByTourIdAndCustomerId(String tourId, String customerId);


    // Find all tourRatings made for a Tour by different customers
    // Make sure, the return is Pageable
    Page<TourRating> findByTourId(String tourId, Pageable pageable);





}
