package com.example.ec.explorecli;

import com.example.ec.explorecli.service.TourPackageService;
import com.example.ec.explorecli.service.TourService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan("com.example.ec.explorecli.domain")
public class ExplorecliApplication {
    private TourService tourService;
    private TourPackageService tourPackageService;

    public static void main(String[] args) {
        SpringApplication.run(ExplorecliApplication.class, args);
    }

}
