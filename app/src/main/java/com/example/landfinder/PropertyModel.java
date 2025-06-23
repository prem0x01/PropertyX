package com.example.landfinder;

public class PropertyModel {
    private String name, price, location, imageUrl;

    public PropertyModel(String name, String price, String location, String imageUrl) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getLocation() { return location; }
    public String getImageUrl() { return imageUrl; }
}
