package com.example.ec.explorecli.repo;

import com.example.ec.explorecli.domain.Difficulty;
import com.example.ec.explorecli.domain.Region;
import com.example.ec.explorecli.domain.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {
    @Query("SELECT t.id, t.price, t.duration, tpkg.name FROM Tour t, TourPackage tpkg WHERE t.tourPackage.code = tpkg.code")
    List<Object[]> findTourDetailsByTourPackageCode();

    List<Tour> findByTourPackageCode(String code);
    Optional<Tour> findByTitle(String title);

    @Query("SELECT id, title, price, duration,region, difficulty FROM Tour WHERE difficulty = :difficulty")
    List<Object[]> findByDifficulty(Difficulty difficulty);

    // find by region
    List<Tour> findByRegion(Region region);

    List<Tour> findByPriceLessThan(double price);

}
