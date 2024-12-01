package com.example.demo.model.dto;

import java.io.Serial;
import java.io.Serializable;

public class TrackerResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String country;
    private String city;
    private String address;

    public TrackerResponseDto() {
    }

    public TrackerResponseDto(Builder builder) {
        this.country = builder.country;
        this.city = builder.city;
        this.address = builder.address;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String country;
        private String city;
        private String address;

        public Builder() {
        }


        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public TrackerResponseDto build() {
            return new TrackerResponseDto(this);
        }
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }
}
