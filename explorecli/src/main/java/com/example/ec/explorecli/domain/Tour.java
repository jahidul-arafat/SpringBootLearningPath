package com.example.ec.explorecli.domain;

import lombok.*;

import javax.persistence.*;

/*
*     "packageType": "Taste of California",
    "title": "Oranges & Apples Tour",
    "blurb": "This three day tour is a taste of some of California's finest fruits. The first day features apple picking, a smoking barbeque and a hayride at a small farm in the historic Oak Glen, and the second take you'll drive to sample the only growing place in the world for pixie tangerines, Ojai, California.",
    "description": "Oak Glen is a tiny hamlet located in the foothill of the San Bernadino Mountain, only 90 minutes from LA, but the climate makes it perfect for stone fruits, such as pears, peaches, and nectarines. However, the jewel of this town is the apple. You'll start the day at Riley's Apple Farm, where you can pick and press as many apples as you can carry. Take a hayride around the farm property and, a fun treat for kids, visit the barn animals. Feast on apple-wood smoked BBQ featuring tri-tip, ribs and chicken apple sausage. Lodging is provided at the quaint Craftin Hills Inn. The next day, it's a short backwoods drive to Ojai, CA, where you'll receive a private tour and picnic lunch at the Churchill Brenneis Orchard. Owners Jim Churchill and Lisa Brenneis are knowledgeable and warm, and you'll leave with a glow and the most delicious tiny tangerines you've ever tasted. Spend a restful night at the Lavender Inn in beautiful downtown Ojai before returning to the regular pace.",
    "bullets": "Apple picking and cider pressing, Hayrides around the apple farm, Lunch with apple-wood smoked BBQ, Lodging at two distinct bed and breakfast inns, Walkabout through Ojai's fragrant orange groves",
    "difficulty": "Easy",
    "length": "3 days",
    "price": "350",
    "region": "Southern California",
    "keywords": "Tasting, Olive Oil, California History, Picnic, Nature, Farming"
*
* */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tour_id")
    private Long id;            // primary key, auto-generated and auto-incremented, No user input required

    // multiple tour can be associated with a single TourPackage
    @ManyToOne
    private TourPackage tourPackage;

    @Column(name = "title")
    private String title;
    @Column(name = "blurb",length = 2000)
    private String blurb;
    @Column(name = "description", length = 2000)
    private String description;
    @Column(name = "bullets",length = 2000)
    private String bullets;

    @Column(name = "difficulty")
    @Enumerated
    private Difficulty difficulty;

    @Column(name = "duration")
    private String duration;
    @Column(name = "price")
    private Double price;

    @Column(name = "region")
    @Enumerated
    private Region region;

    @Column(name = "keywords")
    private String keywords;

    public Tour(TourPackage tourPackage, String title,
                String blurb, String description, String bullets,
                Difficulty difficulty, String duration, Double price, Region region, String keywords) {
        this.tourPackage = tourPackage;
        this.title = title;
        this.blurb = blurb;
        this.description = description;
        this.bullets = bullets;
        this.difficulty = difficulty;
        this.duration = duration;
        this.price = price;
        this.region = region;
        this.keywords = keywords;
    }
}
