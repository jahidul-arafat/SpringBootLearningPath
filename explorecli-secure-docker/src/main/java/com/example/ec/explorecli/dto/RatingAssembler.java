package com.example.ec.explorecli.dto;


import com.example.ec.explorecli.domain.TourRating;
import com.example.ec.explorecli.repo.TourRepository;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class RatingAssembler implements RepresentationModelAssembler<TourRating, RatingDto> {

    private final TourRepository tourRepository;
    private ServletUriComponentsBuilder servletUriComponentsBuilder;

    public RatingAssembler(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void setBuilder(ServletUriComponentsBuilder servletUriComponentsBuilder){
        this.servletUriComponentsBuilder = servletUriComponentsBuilder;
    }

    @Override
    public RatingDto toModel(TourRating tourRating) {
        RatingDto rating = new RatingDto(tourRating.getScore(),
                tourRating.getComment(),
                tourRating.getCustomerId());

        // "self" : ".../ratings/{ratingId}"
        String selfLink = this.servletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        rating.add(Link.of(selfLink).withSelfRel());

        // "tour" : ".../tours/{tourId}/ratings/{customerId}"
        // http://localhost:8080/tours/{tourId}/ratings/{customerId}
        String tourLink = this.servletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tours/{tourId}/ratings/{customerId}")
                .buildAndExpand(tourRating.getTourId(), tourRating.getCustomerId())
                .toUriString();
        rating.add(Link.of(tourLink).withRel("tour"));

        return rating;
    }

    public CustomerTourRefDto toCustomerTourRefDto(TourRating tourRating) {
        CustomerTourRefDto customerTourRefDto = new CustomerTourRefDto();
        customerTourRefDto.setCustomerId(tourRating.getCustomerId());
        customerTourRefDto.setTourId(tourRating.getTourId());
        customerTourRefDto.setComment(tourRating.getComment());

        // "tour" : ".../tours/{tourId}/ratings/{customerId}"
        // http://localhost:8080/tours/{tourId}/ratings/{customerId}
        String tourLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tours/{tourId}/ratings/{customerId}")
                .buildAndExpand(tourRating.getTourId(), tourRating.getCustomerId())
                .toUriString();
        customerTourRefDto.add(Link.of(tourLink).withRel("tour"));


        return customerTourRefDto;
    }


}

