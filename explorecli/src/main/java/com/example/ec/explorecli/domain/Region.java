package com.example.ec.explorecli.domain;

public enum Region {
    Central_Coast("Central Coast"),
    Southern_California("Southern California"),
    Northern_California("Northern California"),
    Varies("Varies");

    private final String name;

    private Region(String name) {
        this.name = name;
    }

    // method to find region by name
    public static Region findByName(String name) {
        for (Region region : Region.values()) {
            if (region.name.equalsIgnoreCase(name)) {
                return region;
            }
        }
        return null;
    }
}
