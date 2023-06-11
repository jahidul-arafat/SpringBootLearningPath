package com.example.ec.explorecli.repo;

import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.domain.TourRatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends JpaRepository<TourRating, TourRatingPK> {
    // list all tourRatings for a tour
    List<TourRating> findByPkTourId(Long pkTourId);

    Optional<TourRating> findByPkTourIdAndPkCustomerID(Long pkTourId, Integer pkCustomerID);

}
