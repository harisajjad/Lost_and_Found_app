package com.example.a71p;

public class LostAndFound {
    private String isLostOrFound;
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;
    private int id;

    public LostAndFound(String isLostOrFound, String name, String phone, String description, String date, String location, int id) {
        this.isLostOrFound = isLostOrFound;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.id = id;
    }

    public String getIsLostOrFound() {
        return isLostOrFound;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }
}