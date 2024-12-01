package com.example.demo.model.dto;

import java.io.Serial;
import java.io.Serializable;


public class TrackerRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String country;
    private String city;
    private String address;
    private float latitude;
    private float longitude;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
