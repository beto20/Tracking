package com.example.demo.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;


public class TrackerRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    @NotEmpty
    private String address;
    @Max(value = 0)
    private float latitude;
    @Max(value = 0)
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
