package com.example.demo.model.mapper;

import com.example.demo.model.dto.TrackerDto;
import com.example.demo.model.dto.TrackerResponseDto;

public class TrackerMapperRx {
    public static TrackerResponseDto toResponseDto(TrackerDto dto) {
        return TrackerResponseDto.builder()
                   .setCountry(dto.getCountry())
                   .setCity(dto.getCity())
                   .setAddress(dto.getAddress())
                   .build();
    }
}
