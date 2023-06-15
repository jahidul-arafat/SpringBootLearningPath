package com.example.ec.explorecli.repo;

import com.example.ec.explorecli.domain.Difficulty;
import com.example.ec.explorecli.domain.Region;
import com.example.ec.explorecli.domain.Tour;
import com.example.ec.explorecli.dto.TourDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query("SELECT t.id, t.price, t.duration, tpkg.name FROM Tour t, TourPackage tpkg WHERE t.tourPackage.code = tpkg.code")
    List<Object[]> findTourDetailsByTourPackageCode();

    List<Tour> findByTourPackageCode(@Param("code") String code);
    Optional<Tour> findByTitle(String title);

    //@Query("SELECT id, title, price, duration,region, difficulty FROM Tour WHERE difficulty = :difficulty")
    List<Tour> findByDifficulty(Difficulty difficulty);

    // find by region
    List<Tour> findByRegion(Region region);


    @Query("SELECT new com.example.ec.explorecli.dto.TourDto(t)" +
            "FROM Tour t where t.price < :price")
    List<TourDto> findByPriceLessThan(@Param("price") double price);

    // method to find tours by price greater than a given price and less than a given price
    @Query("SELECT new com.example.ec.explorecli.dto.TourDto(t) FROM Tour t " +
            "where t.price > :price1 and t.price < :price2")
    List<TourDto> findByPriceGreaterThanAndPriceLessThan(@Param("price1") double price1, @Param("price2") double price2);

    List<Tour> findByTourPackageCodeAndBulletsLike(String code,String bullets);

    // method to find a tour by id
    Optional<Tour> findById(Long tourId);

    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends Tour> List<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Tour entity);

    @Override
    @RestResource(exported = false)
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

}
