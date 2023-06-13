package com.example.explorecli.repositories;

import com.example.explorecli.domain.TourPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// Danger that Spring Data REST expose
/*
*
* As we have seen earlier, the Spring Data Rest enables us not only lookup  the entities, but also allow us to
* Create (HTTP POST), Update (PUT/PATCH) and delete(DELETE) them
* But I dont think we should allow this in public to modify our database in this way
*
* Solution
* --------
* Use @RepositoryRestResource(exported=false) at Class Level
* Use @RestResource(exported=false) at the method level
 */

//@Repository   - this will expose CRUD operations to public which we dont want
// default endpoint http://localhost:9090/tourPackages
// rename the endpoint to http://localhost:9090/packages
@Repository
@RepositoryRestResource(collectionResourceRel = "packages", path = "packages")
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {

    // Non-default method; created by me // not available in CRUD

    // Find the TourPackage by TourPackageName // int the ExploreCalifornia.json we are passing the TourPackageName linked to a Tour
    Optional<TourPackage> findByName(@Param("name") String pkgName); // http://localhost:9090/tourPackages/search

}
